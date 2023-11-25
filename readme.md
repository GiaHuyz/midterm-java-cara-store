# Cara Store

## Giới thiệu

Cara Store là một web thương mại điện tử đơn giản được xây dựng với Java Spring Boot có tích hợp Spring Security. Dữ liệu được lưu trữ bằng MySQL. Dự án bao gồm các tính năng sau:

- Hiển thị sản phẩm: Các sản phẩm được hiển thị với hỗ trợ phân trang.
- Lọc pản phẩm: Người dùng có thể lọc sản phẩm theo nhiều tiêu chí như danh mục, giá cả, thương hiệu, size, sắp xếp theo giá tăng hoặc giảm dần.
- Tìm kiếm sản phẩm: Tính năng tìm kiếm cho phép người dùng tìm sản phẩm theo từ khóa.
- Quản Lý Giỏ Hàng: Người dùng có thể thêm và xoá sản phẩm trong giỏ hàng của họ.
- Thanh Toán và Xem Hóa Đơn: Tính năng thanh toán cho phép người dùng hoàn tất mua hàng và xem hóa đơn của các đơn hàng đã thanh toán.
- Quản Lý Tài Khoản: Người dùng có thể cập nhật thông tin cá nhân và đổi mật khẩu.
- Quản Lý Sản Phẩm (Admin): Người quản trị có thể thêm, xoá và chỉnh sửa thông tin liên quan đến sản phẩm.

## Cấu trúc source code
![code's structure](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/70ebccc5-be1a-41b0-90b3-1eaad3105d7e)

## ERD
![erd](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/b20fcad2-ceb0-4e30-bb07-5e7a91348556)

## Yêu cầu hệ thống

- Java JDK 11 trở lên
- IntelliJ IDEA
- Visual Studio Code
- Docker hoặc XAMPP

## Cài đặt và chạy dự án:

**1. Thiết Lập Cơ Sở Dữ Liệu**

- Sử dụng Docker hoặc XAMPP để tạo một MySQL server.
- Tạo cơ sở dữ liệu cara_store bằng cách chạy script cara_store.sql.
**2. Chạy Backend**
- Clone dự án từ GitHub.
- Cấu hình kết nối cơ sở dữ liệu trong file application.yml, mật khẩu để kết nối hiện tại là **"abc123"**.
- Mở thư mục cara bằng IntelliJ IDEA và chạy file CaraApplication.java.
**3. Chạy Frontend**
- Mở thư mục front-end bằng Visual Studio Code.
- Sử dụng Live Server để chạy file index.html.

## API Reference

#### Admin

- add or update product

```http
POST /api/products
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

Payload: **nếu có key "id" thì sẽ update**

```json
{
	"name": "abc",
	"brandId": "1",
	"categoryId": "1",
	"price": "19.99",
	"description": "dfdfd"
}
```

![add product](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/19c356b6-ad6a-4926-b660-20cf4081c287)

- get all product admin

```http
GET /api/products/admin
```
Mỗi trang sẽ hiện bao nhiêu phần tử tuỳ vào start và length, mặc định là 5 phần tử 1 trang

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `start`         | `int` | Bắt đầu từ thứ x, mặc định là 0         |
| `length`        | `int` | Kết thúc đến thứ y, mặc định là 5        |
| `search[value]` | `String` | Chuỗi tìm kiếm, mặc định là rỗng |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![get all product admin](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/4c502313-52c2-4e44-affd-8445a002fb7e)

- delete product

```http
DELETE /api/products/{id}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `int` | **Required**. product id         |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![delete product](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/fccd6d46-5046-49cb-8916-99fb8736a3b3)

- add or update product detail

```http
POST /api/products/details
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

Payload: **nếu có key "detailId" thì sẽ update**

```json
{
	"productId": 1,
	"detailId": 3,
	"color": "rgb(255,255,255)",
	"size": "M"
}
```
![add or update product detail ](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/2dfe6b6d-1d45-42f0-87eb-b2258d816969)

- delete product detail

```http
DELETE /api/products/details/{id}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `int` | **Required**. detail id         |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![delete product detail](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/196639fd-3a8f-444e-bb98-edeebdd79fc3)

- add image

```http
POST /api/products/images/:id
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `int` | **Required**. product id         |

| Parameter  | Type     | Description             |
| :--------- | :------- | :---------------------- |
| `filename` | `String` | Đường dẫn của file truyền qua form data |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![add image](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/6fe3ebc5-0edd-4bfd-a3b4-82b1eeeb7933)

- update image

```http
POST /api/products/images/{id}/{oldfile}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `String` | **Required**. product id         |
| `oldfile`         | `String` | **Required**. tên của file hiện tại muốn được update  |

| Parameter  | Type     | Description             |
| :--------- | :------- | :---------------------- |
| `filename` | `String` | **Required**. Đường dẫn của file truyền qua form data |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![update image](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/8a786eac-7aa4-4abc-8ae2-1bb4cdd4d66e)

- delete image

```http
DELETE /api/products/{id}/images/{filename}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `int` | **Required**. product id         |
| `filename`         | `String` | **Required**. tên của file muốn delete |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![delete image](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/0f34090f-bfb1-489d-8e08-e52f96a90b67)

#### signup

```http
POST /auth/signup
```

Payload:

```json
{
	"fullName": "abc",
	"username": "a@gmail.com",
	"password": "123",
	"address": "address",
	"phone": "0123456789",
	"roleId": 1
}
```

![signup](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/39093202-d353-4b61-b5a9-b174f1bae178)


#### login

```http
POST /auth/login
```

Payload:

```json
{
	"username": "pele@gmail.com",
	"password": "123"
}
```

![signin](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/8a6a97e0-69f6-4aad-9d7a-c87a357b411f)


#### get product detail

```http
GET /api/products/details/{id}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `int` | **Required**. product id         |

![get product detail](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/2061ff55-4fc2-4485-ad5c-9c6b536ad024)

#### add cart

```http
POST /api/carts
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

Payload:

```json
[
	{
		"productId": "3",
		"detailId": 19,
		"quantity": "1",
		"price": "59.99"
	}
]
```
![add cart](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/2b652af4-8537-4d7b-a0db-73f1a347792f)


#### get user cart

```http
GET /api/carts
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![get user cart](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/df2a5470-788f-41b1-93b9-36111889b03a)

#### add order

```http
POST /api/orders
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![add order](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/89806fec-755d-43de-b8f8-1afdce96e2d7)

#### get user order

```http
GET /api/orders
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![get user order](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/f0bce6e5-4155-4958-9160-ff91f90c571a)

#### update user info

```http
POST /api/users
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

Payload:

```json
{
	"fullName": "pele",
	"address": "abc",
	"phone": "0123456"
}
```

![update user info](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/744b9953-93aa-4c7a-b74d-c5219fd6b1e7)

#### get user info

```http
GET /api/users
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![get user info](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/aa723cf1-3043-4721-a6b4-02e6d1defef6)

#### delete product in user cart

```http
DELETE /api/carts/{id}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `int` | **Required**. cart id         |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![delete product in cart](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/4b059c22-4a6e-4383-a4df-9723d99b4e1b)

#### get all categories

```http
GET /api/categories
```

![get all categories](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/b969bb36-925b-41af-b68d-157271cf44fe)

#### get all brands

```http
GET /api/brands
```
![get all brands](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/c52c58a6-8df5-448e-996b-948b489a503c)

#### get all product user

```http
GET /api/products?category=2&page=1&keyword=nike
```

| Parameter  | Type     | Description             |
| :--------- | :------- | :---------------------- |
| `brand`    | `int` | Lọc theo brand    |
| `category` | `int` | Lọc theo danh mục |
| `sort`     | `String` | Sắp xếp theo giá tăng dần hoặc giảm dần    |
| `minPrice` | `double` | Giá nhỏ nhất |
| `maxPrice` | `double` | Giá lớn nhất |
| `size`     | `String` | Lọc theo size     |
| `page`     | `String` | **Required**. Số trang muốn xem, mặc định là 1     |
| `keyword`  | `String` | Chuỗi tìm kiếm sản phẩm theo tên, miêu tả, brand, price, ...  |

![get all product user](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/57c97835-dc84-4eb6-a31d-ce8fbcdd8ace)

#### change pass

```http
POST /api/users/password
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

Payload:

```json
{
	"currentPassword": "123",
	"newPassword": "1234"
}
```

![change pass](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/2c1b6fda-894d-40aa-b6d9-0a784cbdb993)

#### get image product

```http
POST /api/products/file/{filename}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `filename`         | `String` | **Required**. Tên ảnh muốn tải về         |

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![get image product](https://github.com/GiaHuyz/midterm-java-cara-store/assets/123570938/0e77d116-d84f-4970-81ff-f7b5db1e9959)
