import json
import os
import re
import requests

java_src_file = 'app/src/main/java/io/ipfs/videoshare/App.java'
GW_update_url = 'https://ipfs.github.io/public-gateway-checker/gateways.json'


if __name__ == '__main__':
    response = requests.get(url=GW_update_url)
    res = json.loads(response.text)
    path = os.path.dirname(os.path.abspath(__file__))
    file = os.path.abspath(os.path.join(path, '..', java_src_file))
    with open(file) as json_file:
        data = json_file.read()
    jsonString = re.sub('"', '\\"', json.dumps(res))
    data = re.sub('serverAdd = ".*";', 'serverAdd = "%s";' % jsonString, data)
    with open(file, 'w') as f:
        f.write(data)
    print(res)
    print('Success')

