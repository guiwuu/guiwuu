"""An PaddleOCR-based beancount importer.
"""

__copyright__ = "Copyright (C) 2025  Jun Dai"
__license__ = "GNU GPLv2"

import os
import re
import subprocess
import tempfile
from pathlib import Path
from paddleocr import PaddleOCR
from PIL import Image

from dateutil.parser import parse as parse_datetime

import beangulp
from beangulp import mimetypes
from beangulp.cache import cache
from beangulp.testing import main


@cache
def pdf_to_markdown(filename):
    """Convert a PDF statement to a markdown doc."""
    images = []
    with tempfile.TemporaryDirectory() as temp_dir:
        temp_path = Path(temp_dir)
        subprocess.run(
            [
                "pdfimages",
                "-j", # Extract images in their original format (JPEG/JP2/PNG).
                "-print-filenames", # Print the names of the extracted image files.
                filename,
                str(temp_path / "images"),
            ],
            check=True,
        )
        for image_path in sorted(temp_path.glob("*")):
            images.append(Image.open(image_path))

    params = {
        # use_angle_cls=True: Enables a text angle classifier to detect and correct the text orientation.
        # This is crucial for improving accuracy on scanned documents that may be skewed.
        # For more info, see: https://github.com/PaddlePaddle/PaddleOCR/blob/main/doc/doc_en/whl_en.md
        # Here, we set it to False since the bank statements are usually well-aligned.
        "use_angle_cls": False,
        # lang='en': Specifies the language model to use for recognition (English).
        "lang": "en",
    }
    ocr = PaddleOCR(**params)
    markdown = ""
    for i, image in enumerate(images):
        result = ocr.predict(image)
        if result:
            for line in result:
                markdown += line[1][0] + "\n"
    return markdown


class Importer(beangulp.Importer):
    """An importer for PDF statements."""

    def __init__(self, account_filing):
        self.account_filing = account_filing

    def identify(self, filepath):
        print(f"Identifying {filepath}")
        mimetype, encoding = mimetypes.guess_type(filepath)
        if mimetype != "application/pdf":
            return False

        text = pdf_to_markdown(filepath)
        return bool(text)

    def filename(self, filepath):
        return "statement.pdf"

    def account(self, filepath):
        return self.account_filing

    def date(self, filepath):
        text = pdf_to_markdown(filepath)
        match = re.search("Date: ([^\n]*)", text)
        if match:
            return parse_datetime(match.group(1)).date()


if __name__ == "__main__":
    importer = Importer("Liabilities:Card:MyBank")
    main(importer)
