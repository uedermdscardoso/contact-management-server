
# Contact Management

This is a project about contact management that addresses clean architecture.Contact management must include the following fields: ID, first name, surname, email, phone, Twitter, Skype, and file attachment. And it must be built using Spring Framework and Angular.




## Mentions

The Angular project is cited below.

 - [Contact Managament Client - Angular](https://github.com/uedermdscardoso/contact-management-client)


## MySQL Configuration

Create a folder named docker-composes
```
mkdir docker-composes
```

Enter the docker-composes folder
```
cd docker-composes
```

Create a folder named MySQL
```
mkdir MySQL
```

Enter the MySQL folder
```
cd MySQL
```

Create a docker-compose.yml file
```
version: '3.8'

services:
  db:
    image: mysql:latest
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: master
      MYSQL_USER: admin
      MYSQL_PASSWORD: admin
    volumes:
      - ./mysql_data:/var/lib/mysql
    ports:
      - "3306:3306"

  phpmyadmin:
    image: phpmyadmin/phpmyadmin:latest
    restart: always
    depends_on:
      - db
    environment:
      PMA_HOST: db
      MYSQL_ROOT_PASSWORD: admin
    ports:
      - "8081:80"
```

Run Docker Compose
```
docker compose up -d
```

Access phpMyAdmin using the username "root" and password "admin," or the username "admin" and password "admin."
```
    http://localhost:8081
```

Stop Docker Compose
```
docker compose down -v
```

## Garage Configuration

Configure your garage

Generate a rpc_secret and admin_token
```
openssl rand -hex 32
```

Go back to the docker-composes folder
```
cd ../
```

Create a folder named garage
```
mkdir garage
```

Enter the garage folder
```
cd garage
```

Create a garage.toml file
```
metadata_dir = "/var/lib/garage/meta"
data_dir = "/var/lib/garage/data"

db_engine = "lmdb"
replication_factor = 1
consistency_mode = "consistent"

rpc_bind_addr = "[::]:3901"
rpc_public_addr = "127.0.0.1:3901"
rpc_secret = "your_rpc_secret"

[s3_api]
s3_region = "garage"
api_bind_addr = "[::]:3900"
root_domain = ".s3.localhost"

[admin]
api_bind_addr = "[::]:3903"
admin_token = "your_admin_token"
```

Create a docker-compose.yml file
```
version: '3.8'

services:
  garage:
    image: dxflrs/garage:v2.0.0
    container_name: garage
    restart: unless-stopped
    ports:
      - "3900:3900" # S3 API Gateway
      - "3901:3901" # Internal RPC port
      - "3903:3903" # Admin Portal
    volumes:
      - ./garage.toml:/etc/garage.toml:ro
      - garage_data:/var/lib/garage/data
      - garage_meta:/var/lib/garage/meta

volumes:
  garage_data:
  garage_meta:
```

Stop Docker Compose
```
docker compose down -v
```

Run Docker Compose
```
docker compose up -d
```

Find the Node ID of your Garage container
```
docker exec -it garage /garage node id
```

Assign the node to the Garage layout
```
docker exec -it garage /garage layout assign -z dc1 -c 10G {node-id}
```

Apply the layout
```
docker exec -it garage /garage layout apply --version 1
```

Create an access key (Access Key and Secret Key)
```
docker exec -it garage /garage key create {my-key-s3}
```

Create your first bucket
```
docker exec -it garage /garage bucket create {meu-bucket}
```

Grant permission for the key to use the bucket
```
docker exec -it garage /garage bucket allow --read --write {meu-bucket} --key {my-key-s3}
```

Enter the AWS Access Key ID and AWS Secret Access Key
```
aws configure
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

