let checkoutService = {

    loadCheckout() {
        if (!userService.isLoggedIn()) {
            alert("Please log in to checkout");
            return;
        }

        templateBuilder.build("checkout", {}, "main");

        setTimeout(() => {
            this.renderCart();
        }, 100);
    },

    renderCart() {
        const itemsDiv = document.getElementById("checkout-items");
        const totalSpan = document.getElementById("checkout-total");

        if (!itemsDiv || !totalSpan) {
            console.error("Checkout elements not found!");
            return;
        }

        itemsDiv.innerHTML = "";
        let total = 0;

        const items = cartService.cart.items;

        if (!items || items.length === 0) {
            itemsDiv.innerHTML = "<p>Your cart is empty</p>";
            return;
        }

        items.forEach(item => {
            const lineTotal = item.product.price * item.quantity;
            total += lineTotal;

            const div = document.createElement("div");
            div.classList.add("checkout-item");
            div.textContent = `${item.product.name} x ${item.quantity} — $${lineTotal.toFixed(2)}`;

            itemsDiv.appendChild(div);
        });

        totalSpan.innerText = total.toFixed(2);
    },

   async placeOrder() {
       const token = localStorage.getItem("token");
       if (!token) {
           alert("Please log in");
           return;
       }

       try {
           const response = await fetch(`${config.baseUrl}/orders`, {
               method: "POST",
               headers: {
                   "Authorization": `Bearer ${token}`,
                   "Content-Type": "application/json"
               }
           });

           if (!response.ok) {
               const errorText = await response.text();
               console.error("Order failed:", errorText);
               alert("Order failed: " + errorText);
               return;
           }

           // Check if response has content before parsing JSON
           const contentType = response.headers.get("content-type");
           if (contentType && contentType.includes("application/json")) {
               const order = await response.json();
               console.log("Order placed:", order);
           } else {
               console.log("Order placed (no JSON response)");
           }

           alert("✅ Order placed successfully!");

           // Clear cart and return home
           await cartService.clearCart();
           loadHome();

       } catch (err) {
           console.error("Order error:", err);
           alert("Order failed: " + err.message);
       }
   }
};
window.checkoutService = checkoutService;