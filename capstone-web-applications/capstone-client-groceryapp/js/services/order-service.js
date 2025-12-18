const orderService = {
    async loadOrderHistory() {
        console.log("=== loadOrderHistory called ===");

        const token = localStorage.getItem("token");
        console.log("Token:", token ? "Found" : "Not found");

        if (!token) {
            document.getElementById("order-list").innerHTML = "<p>You must be logged in to view order history.</p>";
            return;
        }

        const container = document.getElementById("order-list");
        container.innerHTML = "<p>Loading orders...</p>";
        console.log("Starting API call...");

        try {
            const response = await axios.get(`${config.baseUrl}/orders/history`, {
                headers: { "Authorization": "Bearer " + token }
            });

            console.log("Response received:", response);
            console.log("Response data:", response.data);

            const orders = response.data;
            this.renderOrderHistory(orders);

        } catch (err) {
            console.error("Error caught:", err);
            console.error("Error response:", err.response);
            container.innerHTML = `<p>Failed to load order history: ${err.message}</p>`;
        }
    },

    renderOrderHistory(orders) {
        console.log("=== renderOrderHistory called ===");
        console.log("Orders:", orders);

        const container = document.getElementById("order-list");
        container.innerHTML = "";

        if (!orders || orders.length === 0) {
            container.innerHTML = "<p>No orders found.</p>";
            return;
        }

        orders.forEach(order => {
            console.log("Rendering order:", order);
            const div = document.createElement("div");
            div.classList.add("order-item");
            div.style.marginBottom = "20px";
            div.style.padding = "15px";
            div.style.border = "1px solid #ddd";
            div.style.borderRadius = "5px";

            div.innerHTML = `
                <h4>Order #${order.orderId}</h4>
                <p><strong>User ID:</strong> ${order.userId}</p>
                <p><strong>Date:</strong> ${order.date}</p>
                <p><strong>Address:</strong> ${order.address}, ${order.city}, ${order.state} ${order.zip}</p>
                <p><strong>Shipping Amount:</strong> $${order.shippingAmount}</p>
            `;
            container.appendChild(div);
        });

        console.log("Finished rendering", orders.length, "orders");
    }
};