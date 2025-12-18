
function showLoginForm()
{
    templateBuilder.build('login-form', {}, 'login');
}

function hideModalForm()
{
    templateBuilder.clear('login');
}

function login()
{
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    userService.login(username, password);
    hideModalForm()
}

function showImageDetailForm(product, imageUrl)
{
    const imageDetail = {
        name: product,
        imageUrl: imageUrl
    };

    templateBuilder.build('image-detail',imageDetail,'login')
}

function loadHome()
{
    templateBuilder.build('home',{},'main')

    productService.search();
    categoryService.getAllCategories(loadCategories);
}

function editProfile()
{
    profileService.loadProfile();
}

function saveProfile()
{
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const phone = document.getElementById("phone").value;
    const email = document.getElementById("email").value;
    const address = document.getElementById("address").value;
    const city = document.getElementById("city").value;
    const state = document.getElementById("state").value;
    const zip = document.getElementById("zip").value;

    const profile = {
        firstName,
        lastName,
        phone,
        email,
        address,
        city,
        state,
        zip
    };

    profileService.updateProfile(profile);
}

function showCart()
{
    cartService.loadCartPage();
}

function clearCart()
{
    cartService.clearCart();
}

function setCategory(control)
{
    productService.addCategoryFilter(control.value);
    productService.search();

}

function setSubcategory(control)
{
    productService.addSubcategoryFilter(control.value);
    productService.search();

}

function setMinPrice(control)
{
    // const slider = document.getElementById("min-price");
    const label = document.getElementById("min-price-display")
    label.innerText = control.value;

    const value = control.value != 0 ? control.value : "";
    productService.addMinPriceFilter(value)
    productService.search();

}

function setMaxPrice(control)
{
    // const slider = document.getElementById("min-price");
    const label = document.getElementById("max-price-display")
    label.innerText = control.value;

    const value = control.value != 200 ? control.value : "";
    productService.addMaxPriceFilter(value)
    productService.search();

}

function closeError(control)
{
    setTimeout(() => {
        control.click();
    },3000);
}

document.addEventListener('DOMContentLoaded', () => {

    loadHome();
});
function showCheckout() {
    const main = document.getElementById("main");
    main.innerHTML = "";

    const div = document.createElement("div");
    div.classList.add("checkout-container");

    const h2 = document.createElement("h2");
    h2.innerText = "Checkout";
    div.appendChild(h2);

    const itemsDiv = document.createElement("div");

    let total = 0;

    cartService.cart.items.forEach(item => {
        const lineTotal = item.product.price * item.quantity;
        total += lineTotal;

        const row = document.createElement("div");
        row.innerText = `${item.product.name} x ${item.quantity} — $${lineTotal.toFixed(2)}`;
        itemsDiv.appendChild(row);
    });

    div.appendChild(itemsDiv);

    const totalDiv = document.createElement("div");
    totalDiv.innerHTML = `<strong>Total:</strong> $${total.toFixed(2)}`;
    div.appendChild(totalDiv);

    const btn = document.createElement("button");
    btn.innerText = "Place Order";
    btn.addEventListener("click", placeOrder);
    div.appendChild(btn);

    main.appendChild(div);
}

async function loadCheckout() {
    const token = localStorage.getItem("token");
    if (!token) return;

    const res = await fetch("/cart", {
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    if (!res.ok) return;

    const cart = await res.json();
    const itemsDiv = document.getElementById("checkout-items");
    const totalSpan = document.getElementById("checkout-total");

    let total = 0;
    itemsDiv.innerHTML = "";

    cart.items.forEach(item => {
        const lineTotal = item.product.price * item.quantity;
        total += lineTotal;

        const div = document.createElement("div");
        div.textContent = `${item.product.name} x ${item.quantity} — $${lineTotal.toFixed(2)}`;
        itemsDiv.appendChild(div);
    });

    totalSpan.textContent = total.toFixed(2);
}
function placeOrder() {

    alert("✅ Order placed!");

    clearCart();

    loadHome();

}
function viewOrderHistory() {
    templateBuilder.build('order-history', {}, 'main');

    // Wait for the template to be inserted into the DOM before loading data
    setTimeout(() => {
        orderService.loadOrderHistory();
    }, 100);
}

function clearOrderHistory() {
    if (confirm("Are you sure you want to clear all order history?")) {
        alert("This feature requires backend implementation.");
    }
}
