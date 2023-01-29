# sns-login-api

- 패키지 구조

```
└── src
		├── main
		│   ├── java
		│   │   └── com
		│   │       └── shop
		│   │           └── projectlion
		│   │               ├── api
		│   │               │   └── health
		│   │               │       └── client
		│   │               │       └── controller
		│   │               │       └── dto
		│   │               │       └── service
		│   │               │   └── login
		│   │               │       └── client
		│   │               │       └── controller
		│   │               │       └── dto
		│   │               │       └── service
		│   │               │   └── logout
		│   │               │       └── controller
		│   │               │       └── service
		│   │               │   └── adminitem
		│   │               │       └── controller
		│   │               │       └── dto
		│   │               │       └── service
		│   │               │   └── token
		│   │               │       └── controller
		│   │               │       └── dto
		│   │               │       └── service
		│   │               ├── domain
		│   │               │   └── common
		│   │               │   └── jwt
		│   │               │       └── constant
		│   │               │       └── dto
		│   │               │       └── service
		│   │               │   └── member
		│   │               │       └── constant
		│   │               │       └── entity
		│   │               │       └── repository
		│   │               │       └── service
		│   │               │       └── exception
		│   │               │   └── item
		│   │               │       └── model
		│   │               │           └── enumclass
		│   │               │       └── repository
		│   │               │       └── service
		│   │               │   └── itemimage
		│   │               │       └── model
		│   │               │       └── repository
		│   │               │       └── service
		│   │               │   └── delivery
		│   │               │       └── model
		│   │               │       └── repository
		│   │               │       └── service
		│   │               │   └── order
		│   │               │       └── model
		│   │               │           └── enumclass
		│   │               │       └── repository
		│   │               │       └── service
		│   │               │   └── orderitem
		│   │               │       └── model
		│   │               │       └── repository
		│   │               │       └── service
		│   │               ├── global
		│   │               │   └── error
		│   │               │   └── config
		│   │               │   └── resolver
		│   │               │   └── util
		│   │               ├── infra
		│   │               └── web
		│   │               │   └── kakaotoken
		│   │               │       └── client
		│   │               │       └── controller
		│   │               │       └── dto
		│   │               │       └── service
```

- API 서버
    - 카카오 로그인    
    ![image](https://user-images.githubusercontent.com/81370558/215298770-1ecda096-9260-4eb9-b748-a664f01e2bee.png)
    ![image](https://user-images.githubusercontent.com/81370558/215298774-91ddbd2c-7aa4-40f0-b9ab-c24b0a48bf9b.png)
    ![image](https://user-images.githubusercontent.com/81370558/215298776-8ba71557-fa6c-4638-9d98-efae861603f0.png)

    - 로그인 / 회원가입    
    ![image](https://user-images.githubusercontent.com/81370558/215298797-72a841ac-828d-4e02-b335-d73734773e6a.png)

- CI/CD
    - develop 브랜치에서 push

    - git Actions    
    ![image](https://user-images.githubusercontent.com/81370558/215298818-30cfbddb-5203-4add-9d1c-5081127c7ed2.png)
    
    - AWS EC2 Server
    ![image](https://user-images.githubusercontent.com/81370558/215298825-e19ae4ec-3440-4304-8cb5-794faeab0ff5.png)

    - docker hub    
    ![image](https://user-images.githubusercontent.com/81370558/215298846-ec44fe06-1f74-4344-8d4f-17a45f3ad734.png)

    - API 서버 health check    
    ![image](https://user-images.githubusercontent.com/81370558/215298862-b8cffded-fe22-4c22-8f86-f3d9590af06a.png)

    
    
    
    
    
