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

![ERD](<../img giua ki/erd.png>)

![Code Structure](<../img giua ki/code's structure.png>)

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

![Alt text](<../img giua ki/add product.png>)

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

![Alt text](<../img giua ki/get all product admin.png>)

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

![Alt text](<../img giua ki/delete product.png>)

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
![Alt text](<../img giua ki/add or update product detail .png>)

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

![Alt text](<../img giua ki/delete product detail.png>)

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

![Alt text](<../img giua ki/add image.png>)

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

![Alt text](<../img giua ki/update image.png>)

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

![Alt text](<../img giua ki/delete image.png>)

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

![Alt text](<../img giua ki/signup.png>)

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

![Alt text](<../img giua ki/signin.png>)

#### product detail

```http
GET /api/products/details/{id}
```

| Pathvariable       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`         | `int` | **Required**. product id         |

![Alt text](<../img giua ki/get product detail.png>)

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

![Alt text](<../img giua ki/add cart.png>)

#### get user cart

```http
GET /api/carts
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![Alt text](<../img giua ki/get user cart.png>)

#### add order

```http
POST /api/orders
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![Alt text](<../img giua ki/add order.png>)

#### get user order

```http
GET /api/orders
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |

![Alt text](<../img giua ki/get user order.png>)

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

![Alt text](<../img giua ki/update user info.png>)

#### get user info

```http
GET /api/users
```

| Header          | Description                |
| :-------------- | :------------------------- |
| `Authorization` | **Required**. Bearer token |


![Alt text](<../img giua ki/get user info.png>)

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

![Alt text](<../img giua ki/delete product in cart.png>)

#### get all categories

```http
GET /api/categories
```

![Alt text](<../img giua ki/get all categories.png>)

#### get all brands

```http
GET /api/brands
```
![Alt text](<../img giua ki/get all brands.png>)

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

![Alt text](<../img giua ki/get all product user.png>)

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

![Alt text](<../img giua ki/change pass.png>)

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

![Alt text](<../img giua ki/get image product.png>)