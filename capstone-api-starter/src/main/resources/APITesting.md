1. log in as user:
-  http://localhost:8080/login
POST
{
"username": "saint",
"password": "password"
}
2. search products
   **ADD AUTHENTICATION TOKEN
GET
- http://localhost:8080/products?cat=1
GET
- http://localhost:8080/products?cat=1&subCategory=fresh
3. log in as admin:
- http://localhost:8080/login
POST
  {
  "username": "PrinceHaywood",
  "password": "password"
  }
4. add a product
   **ADD AUTHENTICATION TOKEN
POST 
- http://localhost:8080/products
  {
  "productId": 63,
  "name": "Teriyaki Chicken",
  "price": 6.99,
  "categoryId": 1,
  "description": "Delicious chicken marinated in teriyaki sauce",
  "subCategory": "Fresh",
  "stock": 50,
  "imageUrl": "teriyaki-chicken.jpg",
  "featured": true
  }
5. delete a product
DELETE
- http://localhost:8080/products/63