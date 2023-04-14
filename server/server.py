# coding:utf-8
import json

from flask import Flask, request
import requests

app = Flask(__name__)

BASE_URL = '/chatGPT/'
GPT_API_KEY = 'sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'


# 接收post请求
@app.route(BASE_URL + 'chat', methods=['POST'])
def chat():
    # 解析请求参数-结果是bytes类型
    data = request.get_data()
    # 将bytes类型转换为json
    json_data = json.loads(data.decode("utf-8"))
    # logging.DEBUG("请求参数", json_data)
    # 请求chatGPT turbo API
    result = requests.post(url="https://api.openai.com/v1/chat/completions",
                           json=json_data,
                           headers={"Authorization": "Bearer " + GPT_API_KEY,
                                    "Content-Type": "application/json"})
    result_text = result.text
    # logging.DEBUG(result_text)
    return result_text


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0', port=12345)
