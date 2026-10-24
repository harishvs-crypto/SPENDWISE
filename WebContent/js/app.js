function toggleAuth() {
    const login = document.getElementById('login-form');
    const register = document.getElementById('register-form');
    if (login.style.display === 'none') {
        login.style.display = 'block';
        register.style.display = 'none';
        document.getElementById('auth-msg').innerText = '';
    } else {
        login.style.display = 'none';
        register.style.display = 'block';
        document.getElementById('auth-msg').innerText = '';
    }
}

async function handleLogin(e) {
    e.preventDefault();
    const user = document.getElementById('login-user').value;
    const pass = document.getElementById('login-pass').value;
    
    try {
        const formData = new URLSearchParams();
        formData.append("username", user);
        formData.append("password", pass);

        const res = await fetch(`LoginServlet`, {
            method: 'POST',
            body: formData,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        const data = await res.json();
        
        if (data.status === 'success') {
            localStorage.setItem("user", JSON.stringify(data));
            window.location.href = "dashboard.html";
        } else {
            document.getElementById('auth-msg').innerText = data.message;
            document.getElementById('auth-msg').style.color = "red";
        }
    } catch(err) {
        console.error("Login failed", err);
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const user = document.getElementById('reg-user').value;
    const pass = document.getElementById('reg-pass').value;
    const budget = document.getElementById('reg-budget').value;
    
    try {
        const formData = new URLSearchParams();
        formData.append("username", user);
        formData.append("password", pass);
        formData.append("budget", budget);

        const res = await fetch(`RegisterServlet`, {
            method: 'POST',
            body: formData,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        const data = await res.json();
        
        if (data.status === 'success') {
            document.getElementById('auth-msg').innerText = "Registration successful! Please login.";
            document.getElementById('auth-msg').style.color = "green";
            setTimeout(toggleAuth, 1000);
        } else {
            document.getElementById('auth-msg').innerText = data.message;
            document.getElementById('auth-msg').style.color = "red";
        }
    } catch(err) {
        console.error(err);
    }
}

function logout() {
    localStorage.removeItem("user");
    window.location.href = "index.html";
}

function checkAuth() {
    const user = localStorage.getItem("user");
    if (!user) {
        window.location.href = "index.html";
        return null;
    }
    return JSON.parse(user);
}

// Data cache for views
let allExpenses = [];

async function fetchExpenses() {
    try {
        const res = await fetch(`ViewExpenseServlet`);
        if (res.status === 401) { logout(); return; }
        const data = await res.json();
        if (data.status === 'error') {
            logout();
        } else {
            allExpenses = data;
        }
    } catch(err) {
        console.error("Failed fetching expenses");
    }
}

async function loadDashboard() {
    const user = checkAuth();
    if (!user) return;
    
    document.getElementById('welcome-text').innerText = `Welcome back, ${user.username}!`;
    
    await fetchExpenses();
    
    let totalExpense = 0;
    const catMap = {};
    
    const tbody = document.getElementById('recent-body');
    tbody.innerHTML = '';
    
    const maxRecent = Math.min(5, allExpenses.length);
    for (let i = 0; i < allExpenses.length; i++) {
        const exp = allExpenses[i];
        totalExpense += exp.amount;
        
        catMap[exp.category] = (catMap[exp.category] || 0) + exp.amount;
        
        if (i < maxRecent) {
            tbody.innerHTML += `<tr>
                <td>${exp.date}</td>
                <td>${getCategoryIcon(exp.category)} ${exp.category}</td>
                <td>₹${exp.amount.toFixed(2)}</td>
                <td>${exp.paymentMode}</td>
            </tr>`;
        }
    }
    
    document.getElementById('total-expense').innerText = `₹${totalExpense.toFixed(2)}`;
    
    const remBudget = user.budget - totalExpense;
    const remElem = document.getElementById('rem-budget');
    remElem.innerText = `₹${remBudget.toFixed(2)}`;
    if (remBudget < 0) {
        remElem.style.color = "var(--danger)";
    }
    
    if (user.budget > 0) {
        let percent = (totalExpense / user.budget) * 100;
        let pBar = document.getElementById('budget-bar');
        pBar.style.width = Math.min(percent, 100) + "%";
        pBar.innerText = percent.toFixed(1) + "%";
        
        let pText = document.getElementById('budget-text');
        if (percent > 0) {
            pText.innerText = `You have used ${percent.toFixed(1)}% of your monthly budget.`;
        } else {
            pText.innerText = "No expenses yet.";
        }
        
        // Multi-threaded alert feature is built for server backup, UI highlights it immediately
        if (percent >= 80) {
            document.getElementById('budget-warning').style.display = "block";
            pBar.style.background = "var(--danger)";
        }
    }
    
    let topCat = "-";
    let maxAmt = 0;
    for (const [cat, amt] of Object.entries(catMap)) {
        if (amt > maxAmt) {
            maxAmt = amt;
            topCat = cat;
        }
    }
    document.getElementById('top-cat').innerText = topCat !== "-" ? `${getCategoryIcon(topCat)} ${topCat}` : topCat;
}

async function handleAddExpense(e) {
    e.preventDefault();
    const amt = document.getElementById('exp-amt').value;
    const cat = document.getElementById('exp-cat').value;
    const date = document.getElementById('exp-date').value;
    const mode = document.getElementById('exp-mode').value;
    const desc = document.getElementById('exp-desc').value;

    try {
        const formData = new URLSearchParams();
        formData.append("amount", amt);
        formData.append("category", cat);
        formData.append("date", date);
        formData.append("paymentMode", mode);
        formData.append("description", desc);

        const res = await fetch(`AddExpenseServlet`, {
            method: 'POST',
            body: formData,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        const data = await res.json();
        
        const msg = document.getElementById('add-msg');
        if (data.status === 'success') {
            msg.innerText = "Expense Added successfully!";
            msg.style.color = "var(--success)";
            e.target.reset();
        } else {
            msg.innerText = data.message || "Failed to add expense";
            msg.style.color = "var(--danger)";
        }
    } catch(err) {
        console.error(err);
    }
}

let sortAsc = false;
async function loadHistory() {
    checkAuth();
    await fetchExpenses();
    renderHistoryTable(allExpenses);
}

function renderHistoryTable(data) {
    const tbody = document.getElementById('history-body');
    tbody.innerHTML = '';
    data.forEach(exp => {
        tbody.innerHTML += `<tr>
            <td>${exp.date}</td>
            <td>${getCategoryIcon(exp.category)} ${exp.category}</td>
            <td>₹${exp.amount.toFixed(2)}</td>
            <td>${exp.paymentMode}</td>
            <td>${exp.description}</td>
            <td><button class="action-btn" onclick="deleteExpense(${exp.id})">Delete</button></td>
        </tr>`;
    });
}

function filterHistory() {
    const sCat = document.getElementById('search-cat').value.toLowerCase();
    const fMonth = document.getElementById('filter-month').value;
    
    let filtered = allExpenses.filter(e => {
        let matchCat = e.category.toLowerCase().includes(sCat);
        let matchMonth = true;
        if (fMonth) {
            matchMonth = e.date.startsWith(fMonth);
        }
        return matchCat && matchMonth;
    });
    
    renderHistoryTable(filtered);
}

function sortHistory() {
    sortAsc = !sortAsc;
    let filtered = [...allExpenses];
    filtered.sort((a,b) => sortAsc ? a.amount - b.amount : b.amount - a.amount);
    renderHistoryTable(filtered);
}

async function deleteExpense(id) {
    if (!confirm("Are you sure you want to delete this expense?")) return;
    try {
        const formData = new URLSearchParams();
        formData.append("id", id);
        
        const res = await fetch(`DeleteExpenseServlet`, {
            method: 'POST',
            body: formData,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        const data = await res.json();
        if (data.status === 'success') {
            loadHistory();
        } else {
            alert("Delete failed.");
        }
    } catch(err) {
        console.error(err);
    }
}

async function loadAnalytics() {
    checkAuth();
    await fetchExpenses();
    if(allExpenses.length === 0) return;
    
    let high = allExpenses[0].amount;
    let low = allExpenses[0].amount;
    let sum = 0;
    
    const catMap = {};
    const dates = new Set();
    
    allExpenses.forEach(e => {
        if (e.amount > high) high = e.amount;
        if (e.amount < low) low = e.amount;
        sum += e.amount;
        catMap[e.category] = (catMap[e.category] || 0) + e.amount;
        dates.add(e.date);
    });
    
    document.getElementById('high-exp').innerText = `₹${high.toFixed(2)}`;
    document.getElementById('low-exp').innerText = `₹${low.toFixed(2)}`;
    
    let unqDays = dates.size > 0 ? dates.size : 1;
    document.getElementById('avg-exp').innerText = `₹${(sum/unqDays).toFixed(2)}`;
    
    const barCont = document.getElementById('category-bars');
    barCont.innerHTML = '';
    
    for (const [cat, amt] of Object.entries(catMap)) {
        let pct = (amt / sum) * 100;
        let color = "var(--primary-color)";
        if (cat === "Food") color = "#FF9F43";
        else if (cat === "Travel") color = "#00CEC9";
        else if (cat === "Shopping") color = "#E84393";
        else if (cat === "Bills") color = "#D63031";
        
        barCont.innerHTML += `
            <div style="margin-bottom: 10px;">
                <div style="display: flex; justify-content: space-between; font-size: 13px;">
                    <span>${getCategoryIcon(cat)} ${cat}</span>
                    <span>${pct.toFixed(1)}% (₹${amt.toFixed(2)})</span>
                </div>
                <div class="progress-container" style="height: 10px; margin-top: 5px; background: #e0e0e0;">
                    <div style="height: 100%; border-radius: 10px; width: ${pct}%; background: ${color};"></div>
                </div>
            </div>
        `;
    }
}

function exportData(type) {
    window.open(`ReportServlet?type=${type}`, '_blank');
}

function toggleDarkMode() {
    document.body.classList.toggle('dark-mode');
}

function getCategoryIcon(cat) {
    const icons = {
        "Food": "🍔",
        "Travel": "🚗",
        "Shopping": "🛍️",
        "Education": "📚",
        "Bills": "💡",
        "Health": "🏥",
        "Others": "📦"
    };
    return icons[cat] || "🏷️";
}

