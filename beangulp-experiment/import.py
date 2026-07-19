#!/usr/bin/env python3

"""
An import script for beangulp.
"""

import beangulp
from importers.paddle_ocr import Importer

# Configure the importers.
importers = [
    Importer("Liabilities:Card:ROGERS:Jun"),
]

# Define hooks, if any.
hooks = []

# Run the ingest process.
if __name__ == "__main__":
    ingest = beangulp.Ingest(importers, hooks)
    ingest()
