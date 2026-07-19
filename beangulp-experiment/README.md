# beangulp-experiment
The project compares frontier AI models for importing various bank statements into the popular plaintext leger software [beanacount](https://beancount.github.io)

## Usage
```shell
# install beancount v3
pip install beancount
pip install paddlepaddle paddleocr 
pip install pdf2image python-poppler # For windows, see https://pypi.org/project/pdf2image/
```
## References
* Comprehensive guide for beancount
  * plaintext accounting - https://plaintextaccounting.org/
  * https://lazy-beancount.xyz/
  * https://reds-rants.netlify.app/personal-finance/the-five-minute-ledger-update/
  * https://awesome-beancount.com/
* Other importers
  * MacOS UI and importer - https://github.com/Nef10/SwiftBeanCount
  * https://github.com/redstreet/beancount_reds_importers
  * New importer framework - https://github.com/beancount/beangulp
    * https://docs.google.com/document/d/1hBfsHZcoHgz5rvhCdP42g2FJ5ouycIMV4H1tfgXpwBU
```
# On windows 11
git clone git@github.com:beancount/beangulp.git
cd beangulp/examples
python -m venv .venv # create in subfolder called ".venv"
pip install pdftotext3 # install pdftotext cli for the acme importer example
python import.py extract ./Downloads > tmp.beancount
```
* Other integration
  * https://docs.google.com/document/d/1Z37bQ45wDtjTPaMQ_x-f33p1trH9fNosEAUgbQXwp30
  * https://github.com/vanto/beanquery-mcp
  * https://github.com/StdioA/beancount-mcp
  * WebUI: fava (https://beancount.github.io/fava/)
    * Custom dashboards: https://github.com/andreasgerstmayr/fava-dashboards
```
# On windows 11
pip install --upgrade fava
pip install git+https://github.com/andreasgerstmayr/fava-dashboards.git
fava ledger.beancount
```
