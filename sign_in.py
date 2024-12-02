import os
import requests

def sign_in():
    url = "https://api.juejin.cn/user_api/v1/signin"  # 假设这是签到API
    headers = {
        'Cookie': os.environ['JUEJIN_COOKIE']
    }
    response = requests.post(url, headers=headers)
    if response.status_code == 200:
        print("签到成功")
    else:
        print(f"签到失败: {response.status_code} {response.text}")

if __name__ == "__main__":
    sign_in()
