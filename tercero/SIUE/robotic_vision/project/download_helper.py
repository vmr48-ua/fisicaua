from urllib.request import urlopen
from io import BytesIO
from zipfile import ZipFile

def download_and_unzip(url, extract_to='.'):
    http_response = urlopen(url)
    zipfile = ZipFile(BytesIO(http_response.read()))
    zipfile.extractall(path=extract_to)

product_group_id = '68942187'
url = 'https://mast.stsci.edu/api/v0.1/Download/bundle.zip?previews=false&obsid=' + product_group_id
destination = r"C:\Users\victor\fisicaua\tercero\SIUE\robotic_vision\project\TESS"

download_and_unzip(url, destination)
# https://mast.stsci.edu/api/v0.1/Download/bundle.zip?previews=false&obsid=68942187