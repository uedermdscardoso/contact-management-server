
# Contact Management

This is a project about contact management that addresses clean architecture.Contact management must include the following fields: ID, first name, surname, email, phone, Twitter, Skype, and file attachment. And it must be built using Spring Framework and Angular.




## Mentions

The Angular project is cited below.

 - [Contact Managament Client - Angular](https://github.com/uedermdscardoso/contact-management-client)


## Installation

Install my-project with npm

```bash
  npm install my-project
  cd my-project
```
    
## Run Locally

Clone the project

```bash
  git clone https://github.com/uedermdscardoso/contact-management-server.git
```

Go to the project directory

```bash
  cd contact-management-server
```

Install dependencies

```bash
  mvn clean install
```

Start the server

```bash
  mvn spring-boot:run
```


## Configuration

Configure your garage

```
docker exec -it garage /garage node id
```

```
docker exec -it garage /garage layout assign -z dc1 -c 10G {node-id}
```

```
docker exec -it garage /garage layout apply --version 1
```

```
docker exec -it garage /garage key create {my-key-s3}
```

```
docker exec -it garage /garage bucket create {meu-bucket}
```

```
docker exec -it garage /garage bucket allow --read --write {meu-bucket} --key {my-key-s3}
```

## Trying the Garage APIs

List available buckets
```
aws --endpoint-url http://localhost:3900 s3 ls
```

List the files within your bucket
```
aws --endpoint-url http://localhost:3900 s3 ls s3://{bucket}
```

Upload a file to the bucket
```
aws --endpoint-url http://localhost:3900 s3 cp {file.txt} s3://{bucket}/
```

Download a file from the bucket to the current folder
```
aws --endpoint-url http://localhost:3900 s3 cp s3://{bucket}/{file.txt} .
```

Delete a file from the bucket
```
aws --endpoint-url http://localhost:3900 s3 rm s3://{bucket}/{file.txt}
```


## API Reference

#### Get Sort Contact List

```http
  GET /api/contact/all/{order_type}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `orderType`       |  `String`   | **Required**. Your order type   |

#### Search Contacts

```http
  GET /api/contact/search/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `attributes` | `AttributeRequestDTO` | **Required**. Your atribute and value |

#### Get Contact

```http
  GET /api/contact/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Integer` | **Required**. Your id |

#### Save Contact

```http
  POST /api/contact/save
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `contactDTO` | `ContactRequestDTO` | **Required**. Your Contact |
| `file`       |  `MultipartFile`   | **Optional**. Your file    |

#### Save Attachment

```http
  POST /api/contact/upload/attachment
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `file`       |  `MultipartFile`   | **Required**. Your file    |

#### Delete Attachment

```http
  DELETE /api/contact/delete/attachment
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `filename`       |  `String`   | **Required**. Your filename   |

#### Put Contact

```http
  PUT /api/contact/{id}/update
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`       |  `Integer`   | **Required**. Your id   |
| `contact`  |  `Contact`   | **Required**. Your contact |

#### Delete Contact

```http
  DELETE /api/contact/{id}/delete
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`       |  `Integer`   | **Required**. Your id   |


## Authors

- [Ueder Cardoso](https://www.github.com/uedermdscardoso)


## License

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

