# README
* Official: https://beancount.github.io/
```
# On windows 11
```
* WebUI: fava (https://beancount.github.io/fava/)
  * Custom dashboards: https://github.com/andreasgerstmayr/fava-dashboards
```
# On windows 11
pip install --upgrade fava
pip install git+https://github.com/andreasgerstmayr/fava-dashboards.git
fava ledger.beancount
```
* Guide - https://lazy-beancount.xyz/
* MacOS UI and importer - https://github.com/Nef10/SwiftBeanCount
* New importer framework - https://github.com/beancount/beangulp
```
# On windows 11
git clone git@github.com:beancount/beangulp.git
cd beangulp/examples
python -m venv .venv # create in subfolder called ".venv"
rm Downloads/Statement.pdf # pdftotext not found error
python import.py extract ./Downloads > tmp.beancount
```