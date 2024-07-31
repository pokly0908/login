## API
<details>
<summary>
회원가입 (POST)
</summary>

#### URL
```
ec2-43-203-119-0.ap-northeast-2.compute.amazonaws.com:8080/signup
```
ec2-43-203-119-0.ap-northeast-2.compute.amazonaws.com:8080/signup
#### Request Body
```json
{
  "username": "525",
  "nickname": "525",
  "password": "525"
}
```
#### Response Body
```json
{
    "username": "525",
    "nickname": "525",
    "authorities": [
        {
            "authorityName": "ROLE_ADMIN"
        }
    ]
}
```
</details>
<details>
<summary>
로그인 (POST)
</summary>

#### URL
```
ec2-43-203-119-0.ap-northeast-2.compute.amazonaws.com:8080/signin
```
#### Request Body
```json
{
    "username": "55",
    "password": "55"
}
```

#### Response Header
```json
{
    "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NSIsInVzZXJuYW1lIjoiNTUiLCJleHAiOjE3MjI0MTMzMTEsImlhdCI6MTcyMjQwOTcxMX0.1vkJv6_vWgjrK9-NNAYZXIOSZ2MNz2JnJaVXSOXtUUo",
    "refreshToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NSIsInVzZXJuYW1lIjoiNTUiLCJleHAiOjE3MjMwMTQ1MTEsImlhdCI6MTcyMjQwOTcxMX0.oCspgK7hWzKsYt7M0c-52lwuJQDFFPyDFo8myDr-NHk"
}
```
#### Response Body
```json
{
    "accessToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NSIsInVzZXJuYW1lIjoiNTUiLCJleHAiOjE3MjI0MTMzMTEsImlhdCI6MTcyMjQwOTcxMX0.1vkJv6_vWgjrK9-NNAYZXIOSZ2MNz2JnJaVXSOXtUUo",
    "refreshToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NSIsInVzZXJuYW1lIjoiNTUiLCJleHAiOjE3MjMwMTQ1MTEsImlhdCI6MTcyMjQwOTcxMX0.oCspgK7hWzKsYt7M0c-52lwuJQDFFPyDFo8myDr-NHk"
}
```
</details>

<details>
<summary>
회원수정 (PUT)
</summary>

#### URL
```
ec2-43-203-119-0.ap-northeast-2.compute.amazonaws.com:8080/signin
```
#### Request Header
```json
  Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NSIsInVzZXJuYW1lIjoiNTUiLCJleHAiOjE3MjI0MTMzMTEsImlhdCI6MTcyMjQwOTcxMX0.1vkJv6_vWgjrK9-NNAYZXIOSZ2MNz2JnJaVXSOXtUUo
```
#### Request Body
```json
{
  "oldPassword": "55",
  "newPassword": "123",
  "newNickname": "123"
}
```
#### Response
```json
    회원정보 수정 완료
```
</details>

<details>
<summary>
회원조회 (GET)
</summary>

#### URL
```json
ec2-43-203-119-0.ap-northeast-2.compute.amazonaws.com:8080/info
```
#### Request Header
```json
  Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NSIsInVzZXJuYW1lIjoiNTUiLCJleHAiOjE3MjI0MTMzMTEsImlhdCI6MTcyMjQwOTcxMX0.1vkJv6_vWgjrK9-NNAYZXIOSZ2MNz2JnJaVXSOXtUUo
```
#### Request Body
```json
{
    "nickname": "123",
    "username": "55"
}
```
</details>

<details>
<summary>
회원탈퇴 (DELETE)
</summary>

#### URL
```json
ec2-43-203-119-0.ap-northeast-2.compute.amazonaws.com:8080/delete
```
#### Request Header
```json
  Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NSIsInVzZXJuYW1lIjoiNTUiLCJleHAiOjE3MjI0MTMzMTEsImlhdCI6MTcyMjQwOTcxMX0.1vkJv6_vWgjrK9-NNAYZXIOSZ2MNz2JnJaVXSOXtUUo
```
#### Request Body
```json
{
    "password": "123"
}
```
#### Response
```json
    회원탈퇴 완료
```
</details>

