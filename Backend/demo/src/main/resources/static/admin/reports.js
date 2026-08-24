const API_BASE_URL = "http://localhost:8080";

const state = {
    analytics: null,
    charts: {},
    selectedYear: new Date().getFullYear(),
    selectedDepartment: "all",
    selectedMonth: ""
};

const elements = {
    menuBtn: document.querySelector(".menu-btn"),
    sidebar: document.querySelector(".sidebar"),
    profile: document.querySelector(".profile"),
    searchInput: document.querySelector(".search-box input"),
    yearFilter: document.getElementById("yearFilter"),
    departmentFilter: document.getElementById("departmentFilter"),
    monthFilter: document.getElementById("monthFilter"),
    totalRevenue: document.getElementById("totalRevenue"),
    totalPatients: document.getElementById("totalPatients"),
    totalAppointments: document.getElementById("totalAppointments"),
    totalDoctors: document.getElementById("totalDoctors"),
    appointmentCompletionRate: document.getElementById("appointmentCompletionRate"),
    patientSatisfaction: document.getElementById("patientSatisfaction"),
    analyticsInsight: document.getElementById("analyticsInsight"),
    adminName: document.getElementById("adminName"),
    adminRole: document.getElementById("adminRole"),
    notificationCount: document.getElementById("notificationCount"),
    lastUpdated: document.getElementById("lastUpdated"),
    revenueChart: document.getElementById("revenueChart"),
    appointmentChart: document.getElementById("appointmentChart"),
    departmentChart: document.getElementById("departmentChart"),
    patientChart: document.getElementById("patientChart"),
    paymentChart: document.getElementById("paymentChart"),
    doctorRadarChart: document.getElementById("doctorRadarChart"),
    weeklyStatusChart: document.getElementById("weeklyStatusChart"),
    revenueChartBadge: document.getElementById("revenueChartBadge"),
    departmentTableBody: document.getElementById("departmentTableBody"),
    doctorTableBody: document.getElementById("doctorTableBody"),
    pdfBtn: document.querySelector(".pdf-btn"),
    excelBtn: document.querySelector(".excel-btn")
};

document.addEventListener(
    "DOMContentLoaded",
    initializeReports
);

async function initializeReports() {
    setupSidebar();
    setupProfile();
    setupFilters();
    setupChartTabs();
    setupSearch();
    setupExports();
    setupTableSorting();
    setupNotifications();
    setupYearFilter();
    loadAdminProfile();
    setupRevealAnimation();
    initializeChartDefaults();
    await loadAnalytics();
}

function setupYearFilter() {
    if (!elements.yearFilter) {
        return;
    }

    const currentYear =
        new Date().getFullYear();

    elements.yearFilter.innerHTML = "";

    for (
        let year = currentYear;
        year >= currentYear - 4;
        year--
    ) {
        const option =
            document.createElement("option");

        option.value = year;
        option.textContent = year;

        elements.yearFilter.appendChild(
            option
        );
    }

    elements.yearFilter.value =
        state.selectedYear;
}

async function loadAnalytics() {
    try {
        const params =
            new URLSearchParams();

        if (
            state.selectedYear !== null &&
            state.selectedYear !== undefined
        ) {
            params.set(
                "year",
                state.selectedYear
            );
        }

        if (
            state.selectedDepartment &&
            state.selectedDepartment !== "all"
        ) {
            params.set(
                "department",
                state.selectedDepartment
            );
        }

        const url =
            `${API_BASE_URL}/api/reports/analytics?${params.toString()}`;

        console.log(
            "Reports API:",
            url
        );

        const response =
            await fetch(
                url,
                {
                    method: "GET",
                    headers: getHeaders()
                }
            );

        if (!response.ok) {
            throw new Error(
                `API Error: ${response.status}`
            );
        }

        state.analytics =
            await response.json();

        console.log(
            "Reports Data:",
            state.analytics
        );

        renderAnalytics();
        updateLastUpdated();

    } catch (error) {
        console.error(
            "Reports loading error:",
            error
        );

        showErrorState(
            "Unable to load reports from backend."
        );
    }
}

function getHeaders() {
    const headers = {
        "Content-Type":
            "application/json"
    };

    const token =
        localStorage.getItem("token");

    if (token) {
        headers.Authorization =
            `Bearer ${token}`;
    }

    return headers;
}

function renderAnalytics() {
    if (!state.analytics) {
        return;
    }

    renderKPIs();
    renderInsight();
    populateDepartmentFilter();
    renderDepartmentTable();
    renderDoctorTable();
    renderRevenueChart();
    renderAppointmentChart();
    renderDepartmentChart();
    renderPatientChart();
    renderPaymentChart();
    renderDoctorPerformanceChart();
    renderWeeklyStatusChart();
    updateNotificationCount();
    animateCards();
}

function renderKPIs() {
    const data =
        state.analytics;

    animateNumber(
        elements.totalRevenue,
        Number(
            data.totalRevenue || 0
        ),
        "₹",
        "",
        0
    );

    animateNumber(
        elements.totalPatients,
        Number(
            data.totalPatients || 0
        ),
        "",
        "",
        0
    );

    animateNumber(
        elements.totalAppointments,
        Number(
            data.totalAppointments || 0
        ),
        "",
        "",
        0
    );

    animateNumber(
        elements.totalDoctors,
        Number(
            data.totalDoctors || 0
        ),
        "",
        "",
        0
    );

    animateNumber(
        elements.appointmentCompletionRate,
        Number(
            data.appointmentCompletionRate || 0
        ),
        "",
        "%",
        1
    );

    animateNumber(
        elements.patientSatisfaction,
        Number(
            data.patientSatisfaction || 0
        ),
        "",
        "/5",
        1
    );
}

function renderInsight() {
    if (!elements.analyticsInsight) {
        return;
    }

    elements.analyticsInsight.textContent =
        state.analytics.analyticsInsight ||
        "Analytics generated from current hospital data.";
}

function populateDepartmentFilter() {
    if (!elements.departmentFilter) {
        return;
    }

    const departments =
        state.analytics.departmentPerformance ||
        [];

    const current =
        state.selectedDepartment;

    elements.departmentFilter.innerHTML = `
        <option value="all">
            All Departments
        </option>
    `;

    departments.forEach(
        department => {
            if (!department.departmentName) {
                return;
            }

            const option =
                document.createElement("option");

            option.value =
                department.departmentName;

            option.textContent =
                department.departmentName;

            elements.departmentFilter.appendChild(
                option
            );
        }
    );

    const exists =
        Array.from(
            elements.departmentFilter.options
        ).some(
            option =>
                option.value === current
        );

    if (exists) {
        elements.departmentFilter.value =
            current;
    } else {
        elements.departmentFilter.value =
            "all";

        state.selectedDepartment =
            "all";
    }
}

function setupFilters() {
    if (elements.yearFilter) {
        elements.yearFilter.addEventListener(
            "change",
            async function () {
                state.selectedYear =
                    Number(this.value);

                await loadAnalytics();
            }
        );
    }

    if (elements.departmentFilter) {
        elements.departmentFilter.addEventListener(
            "change",
            async function () {
                state.selectedDepartment =
                    this.value;

                await loadAnalytics();
            }
        );
    }

    if (elements.monthFilter) {
        elements.monthFilter.addEventListener(
            "change",
            function () {
                state.selectedMonth =
                    this.value;

                applyMonthHighlight();
            }
        );
    }
}

function applyMonthHighlight() {
    const chart =
        state.charts.revenueChart;

    if (
        !chart ||
        !state.selectedMonth
    ) {
        return;
    }

    const parts =
        state.selectedMonth.split("-");

    const selectedMonth =
        Number(parts[1]) - 1;

    const labels =
        chart.data.labels;

    chart.data.datasets[0]
        .backgroundColor =
        labels.map(
            (_, index) => {
                const color =
                    getChartColor(index);

                return index === selectedMonth
                    ? color
                    : hexToRgba(
                        color,
                        0.30
                    );
            }
        );

    chart.update();
}

function renderRevenueChart() {
    const data =
        state.analytics.monthlyRevenue ||
        [];

    const labels =
        data.map(
            item =>
                formatMonthName(
                    item.month
                )
        );

    const values =
        data.map(
            item =>
                Number(
                    item.revenue || 0
                ) / 100000
        );

    destroyChart("revenueChart");

    state.charts.revenueChart =
        new Chart(
            elements.revenueChart,
            {
                type: "bar",

                data: {
                    labels,

                    datasets: [
                        {
                            label:
                                "Revenue (₹ Lakh)",

                            data:
                                values,

                            backgroundColor:
                                labels.map(
                                    (_, index) =>
                                        getChartColor(
                                            index
                                        )
                                ),

                            borderRadius:
                                8,

                            maxBarThickness:
                                48
                        }
                    ]
                },

                options:
                    getBaseChartOptions(
                        "Revenue (₹ Lakh)"
                    )
            }
        );

    if (elements.revenueChartBadge) {
        elements.revenueChartBadge.textContent =
            formatCurrency(
                state.analytics.totalRevenue || 0
            );
    }

    applyMonthHighlight();
}

function renderAppointmentChart() {
    const status =
        state.analytics.appointmentStatus ||
        {};

    const labels = [
        "Pending",
        "Accepted",
        "Completed",
        "Rejected"
    ];

    const values = [
        Number(status.PENDING || 0),
        Number(status.ACCEPTED || 0),
        Number(status.COMPLETED || 0),
        Number(status.REJECTED || 0)
    ];

    destroyChart("appointmentChart");

    state.charts.appointmentChart =
        new Chart(
            elements.appointmentChart,
            {
                type: "bar",

                data: {
                    labels,

                    datasets: [
                        {
                            label:
                                "Appointments",

                            data:
                                values,

                            backgroundColor: [
                                "#f59e0b",
                                "#2563eb",
                                "#16a34a",
                                "#dc2626"
                            ],

                            borderRadius:
                                8,

                            maxBarThickness:
                                48
                        }
                    ]
                },

                options: {
                    ...getBaseChartOptions(
                        "Appointments"
                    ),

                    plugins: {
                        legend: {
                            display:
                                false
                        }
                    }
                }
            }
        );
}

function renderDepartmentChart() {
    const departments =
        state.analytics.departmentPerformance ||
        [];

    const labels =
        departments.map(
            department =>
                department.departmentName
        );

    const values =
        departments.map(
            department =>
                Number(
                    department.appointmentCount ||
                    0
                )
        );

    destroyChart("departmentChart");

    state.charts.departmentChart =
        new Chart(
            elements.departmentChart,
            {
                type: "doughnut",

                data: {
                    labels,

                    datasets: [
                        {
                            data:
                                values,

                            backgroundColor:
                                labels.map(
                                    (_, index) =>
                                        getChartColor(
                                            index
                                        )
                                ),

                            borderColor:
                                "#ffffff",

                            borderWidth:
                                3,

                            hoverOffset:
                                10
                        }
                    ]
                },

                options: {
                    responsive:
                        true,

                    maintainAspectRatio:
                        false,

                    cutout:
                        "60%",

                    animation: {
                        duration:
                            1200
                    },

                    plugins: {
                        legend: {
                            position:
                                "bottom",

                            labels: {
                                padding:
                                    15,

                                usePointStyle:
                                    true
                            }
                        }
                    }
                }
            }
        );
}

function renderPatientChart() {
    const data =
        state.analytics.patientGrowth ||
        [];

    const labels =
        data.map(
            item =>
                formatMonthName(
                    item.month
                )
        );

    const values =
        data.map(
            item =>
                Number(
                    item.patientCount || 0
                )
        );

    destroyChart("patientChart");

    state.charts.patientChart =
        new Chart(
            elements.patientChart,
            {
                type: "line",

                data: {
                    labels,

                    datasets: [
                        {
                            label:
                                "Patients",

                            data:
                                values,

                            borderColor:
                                "#16a34a",

                            backgroundColor:
                                "rgba(22,163,74,.12)",

                            fill:
                                true,

                            tension:
                                0.4,

                            pointRadius:
                                4,

                            pointBackgroundColor:
                                "#16a34a"
                        }
                    ]
                },

                options:
                    getLineChartOptions(
                        "Patients"
                    )
            }
        );
}

function renderPaymentChart() {
    const paymentData =
        state.analytics.paymentMethodRevenue ||
        {};

    const labels =
        Object.keys(
            paymentData
        );

    const values =
        Object.values(
            paymentData
        ).map(
            value =>
                Number(
                    value || 0
                )
        );

    destroyChart("paymentChart");

    state.charts.paymentChart =
        new Chart(
            elements.paymentChart,
            {
                type: "polarArea",

                data: {
                    labels,

                    datasets: [
                        {
                            data:
                                values,

                            backgroundColor:
                                labels.map(
                                    (_, index) =>
                                        getTransparentColor(
                                            index
                                        )
                                ),

                            borderWidth:
                                2,

                            borderColor:
                                "#ffffff"
                        }
                    ]
                },

                options: {
                    responsive:
                        true,

                    maintainAspectRatio:
                        false,

                    animation: {
                        duration:
                            1200
                    },

                    plugins: {
                        legend: {
                            position:
                                "bottom",

                            labels: {
                                padding:
                                    15,

                                usePointStyle:
                                    true
                            }
                        }
                    },

                    scales: {
                        r: {
                            ticks: {
                                display:
                                    false
                            }
                        }
                    }
                }
            }
        );
}

function renderDoctorPerformanceChart() {
    const doctors =
        state.analytics.doctorPerformance ||
        [];

    const topDoctors =
        [...doctors]
            .sort(
                (a, b) =>
                    Number(
                        b.appointmentCount || 0
                    ) -
                    Number(
                        a.appointmentCount || 0
                    )
            )
            .slice(0, 3);

    destroyChart(
        "doctorRadarChart"
    );

    if (topDoctors.length === 0) {
        return;
    }

    const maxPatients =
        Math.max(
            ...topDoctors.map(
                doctor =>
                    Number(
                        doctor.patientCount || 0
                    )
            ),
            1
        );

    const maxAppointments =
        Math.max(
            ...topDoctors.map(
                doctor =>
                    Number(
                        doctor.appointmentCount || 0
                    )
            ),
            1
        );

    const maxRevenue =
        Math.max(
            ...topDoctors.map(
                doctor =>
                    Number(
                        doctor.revenue || 0
                    )
            ),
            1
        );

    const datasets =
        topDoctors.map(
            (doctor, index) => {
                const color =
                    getChartColor(index);

                return {
                    label:
                        doctor.doctorName ||
                        "Doctor",

                    data: [
                        normalize(
                            Number(
                                doctor.patientCount || 0
                            ),
                            maxPatients
                        ),

                        normalize(
                            Number(
                                doctor.appointmentCount || 0
                            ),
                            maxAppointments
                        ),

                        normalize(
                            Number(
                                doctor.rating || 0
                            ),
                            5
                        ),

                        normalize(
                            Number(
                                doctor.revenue || 0
                            ),
                            maxRevenue
                        )
                    ],

                    borderColor:
                        color,

                    backgroundColor:
                        hexToRgba(
                            color,
                            0.12
                        ),

                    pointBackgroundColor:
                        color,

                    borderWidth:
                        2
                };
            }
        );

    state.charts.doctorRadarChart =
        new Chart(
            elements.doctorRadarChart,
            {
                type: "radar",

                data: {
                    labels: [
                        "Patients",
                        "Appointments",
                        "Rating",
                        "Revenue"
                    ],

                    datasets
                },

                options: {
                    responsive:
                        true,

                    maintainAspectRatio:
                        false,

                    scales: {
                        r: {
                            beginAtZero:
                                true,

                            min:
                                0,

                            max:
                                100,

                            ticks: {
                                stepSize:
                                    20
                            }
                        }
                    },

                    plugins: {
                        legend: {
                            position:
                                "bottom",

                            labels: {
                                padding:
                                    15,

                                usePointStyle:
                                    true
                            }
                        }
                    }
                }
            }
        );
}

function renderWeeklyStatusChart() {
    const data =
        state.analytics.weeklyAppointmentStatus ||
        [];

    const labels =
        data.map(
            item =>
                formatDayName(
                    item.day
                )
        );

    const completed =
        data.map(
            item =>
                Number(
                    item.completed || 0
                )
        );

    const cancelled =
        data.map(
            item =>
                Number(
                    item.cancelled || 0
                )
        );

    const pending =
        data.map(
            item =>
                Number(
                    item.pending || 0
                )
        );

    destroyChart(
        "weeklyStatusChart"
    );

    state.charts.weeklyStatusChart =
        new Chart(
            elements.weeklyStatusChart,
            {
                type: "bar",

                data: {
                    labels,

                    datasets: [
                        {
                            label:
                                "Completed",

                            data:
                                completed,

                            backgroundColor:
                                "#16a34a",

                            borderRadius:
                                6
                        },

                        {
                            label:
                                "Cancelled",

                            data:
                                cancelled,

                            backgroundColor:
                                "#dc2626",

                            borderRadius:
                                6
                        },

                        {
                            label:
                                "Pending",

                            data:
                                pending,

                            backgroundColor:
                                "#f59e0b",

                            borderRadius:
                                6
                        }
                    ]
                },

                options: {
                    responsive:
                        true,

                    maintainAspectRatio:
                        false,

                    scales: {
                        x: {
                            stacked:
                                true,

                            grid: {
                                display:
                                    false
                            }
                        },

                        y: {
                            stacked:
                                true,

                            beginAtZero:
                                true
                        }
                    },

                    plugins: {
                        legend: {
                            position:
                                "bottom",

                            labels: {
                                padding:
                                    15,

                                usePointStyle:
                                    true
                            }
                        }
                    }
                }
            }
        );
}

function renderDepartmentTable() {
    const tbody =
        elements.departmentTableBody;

    if (!tbody) {
        return;
    }

    const departments =
        state.analytics.departmentPerformance ||
        [];

    tbody.innerHTML = "";

    if (!departments.length) {
        tbody.innerHTML = `
            <tr>
                <td
                    colspan="6"
                    style="
                        text-align:center;
                        padding:25px;
                        color:#64748b;
                    "
                >
                    No department data available.
                </td>
            </tr>
        `;

        return;
    }

    departments.forEach(
        department => {
            const row =
                document.createElement(
                    "tr"
                );

            const performance =
                department.performance ||
                "Average";

            row.innerHTML = `
                <td>
                    <strong>
                        ${escapeHtml(
                department.departmentName ||
                "--"
            )}
                    </strong>
                </td>

                <td>
                    ${formatNumber(
                department.doctorCount
            )}
                </td>

                <td>
                    ${formatNumber(
                department.patientCount
            )}
                </td>

                <td>
                    ${formatNumber(
                department.appointmentCount
            )}
                </td>

                <td>
                    ${formatCurrency(
                department.revenue
            )}
                </td>

                <td>
                    <span
                        class="status ${getPerformanceClass(
                performance
            )}"
                    >
                        ${escapeHtml(
                performance
            )}
                    </span>
                </td>
            `;

            tbody.appendChild(
                row
            );
        }
    );
}

function renderDoctorTable() {
    const tbody =
        elements.doctorTableBody;

    if (!tbody) {
        return;
    }

    const doctors =
        state.analytics.doctorPerformance ||
        [];

    tbody.innerHTML = "";

    if (!doctors.length) {
        tbody.innerHTML = `
            <tr>
                <td
                    colspan="6"
                    style="
                        text-align:center;
                        padding:25px;
                        color:#64748b;
                    "
                >
                    No doctor performance data available.
                </td>
            </tr>
        `;

        return;
    }

    const sortedDoctors =
        [...doctors].sort(
            (a, b) =>
                Number(
                    b.appointmentCount || 0
                ) -
                Number(
                    a.appointmentCount || 0
                )
        );

    sortedDoctors.forEach(
        doctor => {
            const row =
                document.createElement(
                    "tr"
                );

            row.innerHTML = `
                <td>
                    <strong>
                        ${escapeHtml(
                doctor.doctorName ||
                "--"
            )}
                    </strong>
                </td>

                <td>
                    ${escapeHtml(
                doctor.department ||
                "--"
            )}
                </td>

                <td>
                    ${formatNumber(
                doctor.patientCount
            )}
                </td>

                <td>
                    ${formatNumber(
                doctor.appointmentCount
            )}
                </td>

                <td>
                    ${Number(
                doctor.rating || 0
            ).toFixed(1)}
                    ⭐
                </td>

                <td>
                    ${formatCurrency(
                doctor.revenue
            )}
                </td>
            `;

            tbody.appendChild(
                row
            );
        }
    );
}

function setupChartTabs() {
    const buttons =
        document.querySelectorAll(
            ".tab-btn"
        );

    const cards =
        document.querySelectorAll(
            ".chart-card"
        );

    buttons.forEach(
        button => {
            button.addEventListener(
                "click",
                function () {
                    buttons.forEach(
                        item =>
                            item.classList.remove(
                                "active"
                            )
                    );

                    this.classList.add(
                        "active"
                    );

                    const filter =
                        this.dataset.filter;

                    cards.forEach(
                        card => {
                            const categories =
                                (
                                    card.dataset.category ||
                                    ""
                                ).split(" ");

                            const show =
                                filter === "all" ||
                                categories.includes(
                                    filter
                                );

                            if (show) {
                                card.classList.remove(
                                    "gone"
                                );
                            } else {
                                card.classList.add(
                                    "gone"
                                );
                            }
                        }
                    );
                }
            );
        }
    );
}

function setupSearch() {
    if (!elements.searchInput) {
        return;
    }

    elements.searchInput.addEventListener(
        "input",
        function () {
            const value =
                this.value
                    .trim()
                    .toLowerCase();

            document
                .querySelectorAll(
                    "tbody tr"
                )
                .forEach(
                    row => {
                        const text =
                            row.innerText
                                .toLowerCase();

                        row.style.display =
                            !value ||
                                text.includes(
                                    value
                                )
                                ? ""
                                : "none";
                    }
                );
        }
    );
}

function setupSidebar() {
    if (
        !elements.menuBtn ||
        !elements.sidebar
    ) {
        return;
    }

    elements.menuBtn.addEventListener(
        "click",
        function () {
            if (
                window.innerWidth <=
                900
            ) {
                const current =
                    elements.sidebar.style.left;

                elements.sidebar.style.left =
                    current === "0px"
                        ? "-270px"
                        : "0";
            }
        }
    );

    document.addEventListener(
        "click",
        function (event) {
            if (
                window.innerWidth <=
                900
            ) {
                if (
                    !elements.sidebar.contains(
                        event.target
                    ) &&
                    !elements.menuBtn.contains(
                        event.target
                    )
                ) {
                    elements.sidebar.style.left =
                        "-270px";
                }
            }
        }
    );
}

function setupProfile() {
    if (!elements.profile) {
        return;
    }

    elements.profile.addEventListener(
        "click",
        function () {
            window.location.href =
                "profile.html";
        }
    );
}

async function loadAdminProfile() {

    try {

        const adminId =
            localStorage.getItem("adminId");

        let admin = null;

        const storedAdmin =
            localStorage.getItem("currentAdmin") ||
            localStorage.getItem("admin");

        if (storedAdmin) {

            try {

                admin =
                    JSON.parse(
                        storedAdmin
                    );

            } catch (error) {

                console.error(
                    "Admin data parse error:",
                    error
                );
            }
        }

        if (adminId) {

            const response =
                await fetch(
                    `${API_BASE_URL}/api/admin/${adminId}`,
                    {
                        method: "GET",
                        headers: getHeaders()
                    }
                );

            if (response.ok) {

                admin =
                    await response.json();

                localStorage.setItem(
                    "currentAdmin",
                    JSON.stringify(admin)
                );

                localStorage.setItem(
                    "admin",
                    JSON.stringify(admin)
                );
            }
        }

        if (elements.adminName) {

            elements.adminName.textContent =
                admin?.fullName ||
                admin?.name ||
                admin?.adminName ||
                "Admin";
        }

        if (elements.adminRole) {

            elements.adminRole.textContent =
                admin?.role ||
                admin?.adminRole ||
                "System Administrator";
        }

        const headerProfileImage =
            document.getElementById(
                "headerProfileImage"
            );

        if (
            headerProfileImage &&
            admin?.profileImage
        ) {

            const cleanName =
                String(
                    admin.profileImage
                )
                    .split("/")
                    .pop()
                    .split("\\")
                    .pop();

            headerProfileImage.src =
                `${API_BASE_URL}/profile-images/${encodeURIComponent(
                    cleanName
                )}`;
        }

    } catch (error) {

        console.error(
            "Admin profile load error:",
            error
        );
    }
}

function setupNotifications() {
    const notify =
        document.querySelector(
            ".notify"
        );

    if (!notify) {
        return;
    }

    notify.addEventListener(
        "click",
        function () {
            if (!state.analytics) {
                showToast(
                    "Reports data is not loaded.",
                    "warning",
                    "fa-triangle-exclamation"
                );

                return;
            }

            const pending =
                Number(
                    state.analytics
                        .appointmentStatus
                        ?.PENDING ||
                    0
                );

            if (pending > 0) {
                showToast(
                    `${pending} pending appointment(s).`,
                    "warning",
                    "fa-calendar-check"
                );
            } else {
                showToast(
                    "No pending appointments.",
                    "success",
                    "fa-circle-check"
                );
            }
        }
    );
}

function updateNotificationCount() {
    if (
        !elements.notificationCount ||
        !state.analytics
    ) {
        return;
    }

    elements.notificationCount.textContent =
        Number(
            state.analytics
                .appointmentStatus
                ?.PENDING ||
            0
        );
}

function setupExports() {
    if (elements.pdfBtn) {
        elements.pdfBtn.addEventListener(
            "click",
            exportPDF
        );
    }

    if (elements.excelBtn) {
        elements.excelBtn.addEventListener(
            "click",
            exportExcel
        );
    }
}

function exportPDF() {
    if (!state.analytics) {
        showToast(
            "Analytics data is not loaded yet.",
            "warning",
            "fa-triangle-exclamation"
        );

        return;
    }

    const printWindow =
        window.open(
            "",
            "_blank"
        );

    if (!printWindow) {
        showToast(
            "Please allow pop-ups for PDF export.",
            "warning",
            "fa-triangle-exclamation"
        );

        return;
    }

    printWindow.document.write(
        buildPrintableReport()
    );

    printWindow.document.close();

    setTimeout(
        () => {
            printWindow.focus();
            printWindow.print();
        },
        300
    );
}

function buildPrintableReport() {
    const data =
        state.analytics;

    const departments =
        data.departmentPerformance ||
        [];

    const doctors =
        data.doctorPerformance ||
        [];

    return `
        <!DOCTYPE html>
        <html>
        <head>
            <title>OptiHealth Reports</title>

            <style>
                body {
                    font-family: Arial, sans-serif;
                    padding: 30px;
                    color: #111827;
                }

                h1 {
                    color: #0f766e;
                }

                .summary {
                    display: grid;
                    grid-template-columns:
                        repeat(4, 1fr);
                    gap: 15px;
                    margin: 20px 0;
                }

                .box {
                    border: 1px solid #ddd;
                    padding: 15px;
                    border-radius: 10px;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 15px;
                }

                th,
                td {
                    padding: 9px;
                    border: 1px solid #ddd;
                    text-align: left;
                }

                th {
                    background: #f1f5f9;
                }
            </style>
        </head>

        <body>

            <h1>
                OptiHealth Reports & Analytics
            </h1>

            <p>
                Year:
                ${state.selectedYear}
            </p>

            <div class="summary">

                <div class="box">
                    <strong>
                        Total Revenue
                    </strong>

                    <h2>
                        ${formatCurrency(
        data.totalRevenue
    )}
                    </h2>
                </div>

                <div class="box">
                    <strong>
                        Total Patients
                    </strong>

                    <h2>
                        ${formatNumber(
        data.totalPatients
    )}
                    </h2>
                </div>

                <div class="box">
                    <strong>
                        Total Appointments
                    </strong>

                    <h2>
                        ${formatNumber(
        data.totalAppointments
    )}
                    </h2>
                </div>

                <div class="box">
                    <strong>
                        Completion Rate
                    </strong>

                    <h2>
                        ${Number(
        data.appointmentCompletionRate ||
        0
    ).toFixed(1)}%
                    </h2>
                </div>

            </div>

            <h2>
                Department Statistics
            </h2>

            <table>

                <thead>
                    <tr>
                        <th>Department</th>
                        <th>Doctors</th>
                        <th>Patients</th>
                        <th>Appointments</th>
                        <th>Revenue</th>
                        <th>Performance</th>
                    </tr>
                </thead>

                <tbody>

                    ${departments.map(
        department => `
                            <tr>
                                <td>
                                    ${escapeHtml(
            department.departmentName ||
            "--"
        )}
                                </td>

                                <td>
                                    ${formatNumber(
            department.doctorCount
        )}
                                </td>

                                <td>
                                    ${formatNumber(
            department.patientCount
        )}
                                </td>

                                <td>
                                    ${formatNumber(
            department.appointmentCount
        )}
                                </td>

                                <td>
                                    ${formatCurrency(
            department.revenue
        )}
                                </td>

                                <td>
                                    ${escapeHtml(
            department.performance ||
            "--"
        )}
                                </td>
                            </tr>
                        `
    ).join("")}

                </tbody>

            </table>

            <h2>
                Doctor Performance
            </h2>

            <table>

                <thead>
                    <tr>
                        <th>Doctor</th>
                        <th>Department</th>
                        <th>Patients</th>
                        <th>Appointments</th>
                        <th>Rating</th>
                        <th>Revenue</th>
                    </tr>
                </thead>

                <tbody>

                    ${doctors.map(
        doctor => `
                            <tr>
                                <td>
                                    ${escapeHtml(
            doctor.doctorName ||
            "--"
        )}
                                </td>

                                <td>
                                    ${escapeHtml(
            doctor.department ||
            "--"
        )}
                                </td>

                                <td>
                                    ${formatNumber(
            doctor.patientCount
        )}
                                </td>

                                <td>
                                    ${formatNumber(
            doctor.appointmentCount
        )}
                                </td>

                                <td>
                                    ${Number(
            doctor.rating ||
            0
        ).toFixed(1)}
                                </td>

                                <td>
                                    ${formatCurrency(
            doctor.revenue
        )}
                                </td>
                            </tr>
                        `
    ).join("")}

                </tbody>

            </table>

            <h2>
                Analytics Summary
            </h2>

            <p>
                ${escapeHtml(
        data.analyticsInsight ||
        "--"
    )}
            </p>

        </body>
        </html>
    `;
}

function exportExcel() {
    if (!state.analytics) {
        showToast(
            "Analytics data is not loaded yet.",
            "warning",
            "fa-triangle-exclamation"
        );

        return;
    }

    const csv =
        buildCSV();

    const blob =
        new Blob(
            [csv],
            {
                type:
                    "text/csv;charset=utf-8;"
            }
        );

    const url =
        URL.createObjectURL(
            blob
        );

    const link =
        document.createElement(
            "a"
        );

    link.href =
        url;

    link.download =
        `OptiHealth-Reports-${state.selectedYear}.csv`;

    document.body.appendChild(
        link
    );

    link.click();

    link.remove();

    URL.revokeObjectURL(
        url
    );

    showToast(
        "Excel-compatible report downloaded.",
        "success",
        "fa-file-excel"
    );
}

function buildCSV() {
    const data =
        state.analytics;

    const rows = [];

    rows.push([
        "OptiHealth Reports & Analytics"
    ]);

    rows.push([
        `Year: ${state.selectedYear}`
    ]);

    rows.push([
        `Department: ${state.selectedDepartment}`
    ]);

    rows.push([]);

    rows.push([
        "Metric",
        "Value"
    ]);

    rows.push([
        "Total Revenue",
        data.totalRevenue || 0
    ]);

    rows.push([
        "Total Patients",
        data.totalPatients || 0
    ]);

    rows.push([
        "Total Appointments",
        data.totalAppointments || 0
    ]);

    rows.push([
        "Total Doctors",
        data.totalDoctors || 0
    ]);

    rows.push([
        "Appointment Completion Rate",
        data.appointmentCompletionRate || 0
    ]);

    rows.push([
        "Patient Satisfaction",
        data.patientSatisfaction || 0
    ]);

    rows.push([]);

    rows.push([
        "Department",
        "Doctors",
        "Patients",
        "Appointments",
        "Revenue",
        "Performance"
    ]);

    (
        data.departmentPerformance ||
        []
    ).forEach(
        department => {
            rows.push([
                department.departmentName ||
                "",
                department.doctorCount ||
                0,
                department.patientCount ||
                0,
                department.appointmentCount ||
                0,
                department.revenue ||
                0,
                department.performance ||
                ""
            ]);
        }
    );

    rows.push([]);

    rows.push([
        "Doctor",
        "Department",
        "Patients",
        "Appointments",
        "Rating",
        "Revenue"
    ]);

    (
        data.doctorPerformance ||
        []
    ).forEach(
        doctor => {
            rows.push([
                doctor.doctorName ||
                "",
                doctor.department ||
                "",
                doctor.patientCount ||
                0,
                doctor.appointmentCount ||
                0,
                doctor.rating ||
                0,
                doctor.revenue ||
                0
            ]);
        }
    );

    return rows
        .map(
            row =>
                row
                    .map(
                        value =>
                            csvEscape(
                                value
                            )
                    )
                    .join(",")
        )
        .join("\n");
}

function setupTableSorting() {
    document
        .querySelectorAll(
            "th.sortable"
        )
        .forEach(
            header => {
                header.addEventListener(
                    "click",
                    function () {
                        const table =
                            this.closest(
                                "table"
                            );

                        const tbody =
                            table.querySelector(
                                "tbody"
                            );

                        if (!tbody) {
                            return;
                        }

                        const index =
                            Array.from(
                                this.parentElement
                                    .children
                            ).indexOf(
                                this
                            );

                        const type =
                            this.dataset.type ||
                            "text";

                        const ascending =
                            !this.classList.contains(
                                "asc"
                            );

                        table
                            .querySelectorAll(
                                "th.sortable"
                            )
                            .forEach(
                                th =>
                                    th.classList.remove(
                                        "asc",
                                        "desc"
                                    )
                            );

                        this.classList.add(
                            ascending
                                ? "asc"
                                : "desc"
                        );
                        const rows =
                            Array.from(
                                tbody.querySelectorAll(
                                    "tr"
                                )
                            );

                        rows.sort(
                            (a, b) => {
                                let valueA =
                                    a.children[
                                        index
                                    ]?.innerText
                                        .trim() ||
                                    "";

                                let valueB =
                                    b.children[
                                        index
                                    ]?.innerText
                                        .trim() ||
                                    "";

                                if (
                                    type ===
                                    "number"
                                ) {
                                    valueA =
                                        parseFloat(
                                            valueA.replace(
                                                /[^0-9.-]/g,
                                                ""
                                            )
                                        ) ||
                                        0;

                                    valueB =
                                        parseFloat(
                                            valueB.replace(
                                                /[^0-9.-]/g,
                                                ""
                                            )
                                        ) ||
                                        0;

                                    return ascending
                                        ? valueA -
                                        valueB
                                        : valueB -
                                        valueA;
                                }

                                return ascending
                                    ? valueA.localeCompare(
                                        valueB
                                    )
                                    : valueB.localeCompare(
                                        valueA
                                    );
                            }
                        );

                        rows.forEach(
                            row =>
                                tbody.appendChild(
                                    row
                                )
                        );
                    }
                );
            }
        );
}

function setupRevealAnimation() {
    document
        .querySelectorAll(
            ".reveal"
        )
        .forEach(
            element =>
                element.classList.add(
                    "visible"
                )
        );
}

function animateCards() {
    document
        .querySelectorAll(
            ".report-card"
        )
        .forEach(
            (card, index) => {
                card.style.animation =
                    "none";

                card.offsetHeight;

                card.style.animation =
                    `fadeCard .5s ease ${index * 0.08}s both`;
            }
        );
}

function initializeChartDefaults() {
    if (
        typeof Chart ===
        "undefined"
    ) {
        console.error(
            "Chart.js is not loaded."
        );

        return;
    }

    Chart.defaults.font.family =
        "'Poppins', sans-serif";

    Chart.defaults.color =
        "#64748b";
}

function getBaseChartOptions(
    label
) {
    return {
        responsive:
            true,

        maintainAspectRatio:
            false,

        animation: {
            duration:
                1000
        },

        plugins: {
            legend: {
                display:
                    false
            },

            tooltip: {
                callbacks: {
                    label:
                        context =>
                            `${label}: ${context.raw}`
                }
            }
        },

        scales: {
            y: {
                beginAtZero:
                    true,

                grid: {
                    color:
                        "#f1f5f9"
                }
            },

            x: {
                grid: {
                    display:
                        false
                }
            }
        }
    };
}

function getLineChartOptions(
    label
) {
    return {
        responsive:
            true,

        maintainAspectRatio:
            false,

        animation: {
            duration:
                1000
        },

        plugins: {
            legend: {
                display:
                    false
            },

            tooltip: {
                callbacks: {
                    label:
                        context =>
                            `${label}: ${context.raw}`
                }
            }
        },

        scales: {
            y: {
                beginAtZero:
                    true,

                grid: {
                    color:
                        "#f1f5f9"
                }
            },

            x: {
                grid: {
                    display:
                        false
                }
            }
        }
    };
}

function destroyChart(
    name
) {
    if (
        state.charts[name]
    ) {
        state.charts[name].destroy();
        state.charts[name] =
            null;
    }
}

function animateNumber(
    element,
    target,
    prefix = "",
    suffix = "",
    decimals = 0
) {
    if (!element) {
        return;
    }

    const end =
        Number(target) || 0;

    const duration =
        800;

    const start =
        performance.now();

    function frame(
        currentTime
    ) {
        const progress =
            Math.min(
                (
                    currentTime -
                    start
                ) /
                duration,
                1
            );

        const eased =
            1 -
            Math.pow(
                1 - progress,
                3
            );

        const value =
            end *
            eased;

        element.textContent =
            `${prefix}${value.toLocaleString(
                "en-IN",
                {
                    minimumFractionDigits:
                        decimals,

                    maximumFractionDigits:
                        decimals
                }
            )}${suffix}`;

        if (
            progress <
            1
        ) {
            requestAnimationFrame(
                frame
            );
        }
    }

    requestAnimationFrame(
        frame
    );
}

function updateLastUpdated() {
    if (
        !elements.lastUpdated
    ) {
        return;
    }

    elements.lastUpdated.textContent =
        "Last updated: " +
        new Date().toLocaleTimeString(
            "en-IN",
            {
                hour:
                    "2-digit",

                minute:
                    "2-digit",

                second:
                    "2-digit"
            }
        );
}

function showErrorState(
    message
) {
    if (
        elements.analyticsInsight
    ) {
        elements.analyticsInsight.textContent =
            message;
    }

    if (
        elements.departmentTableBody
    ) {
        elements.departmentTableBody.innerHTML = `
            <tr>
                <td
                    colspan="6"
                    style="
                        text-align:center;
                        padding:25px;
                        color:#dc2626;
                    "
                >
                    Unable to load department data.
                </td>
            </tr>
        `;
    }

    if (
        elements.doctorTableBody
    ) {
        elements.doctorTableBody.innerHTML = `
            <tr>
                <td
                    colspan="6"
                    style="
                        text-align:center;
                        padding:25px;
                        color:#dc2626;
                    "
                >
                    Unable to load doctor data.
                </td>
            </tr>
        `;
    }

    showToast(
        message,
        "warning",
        "fa-triangle-exclamation"
    );
}

function formatCurrency(
    value
) {
    return (
        "₹" +
        Number(
            value || 0
        ).toLocaleString(
            "en-IN",
            {
                maximumFractionDigits:
                    2
            }
        )
    );
}

function formatNumber(
    value
) {
    return Number(
        value || 0
    ).toLocaleString(
        "en-IN"
    );
}

function formatMonthName(
    month
) {
    if (!month) {
        return "--";
    }

    const map = {
        JANUARY: "Jan",
        FEBRUARY: "Feb",
        MARCH: "Mar",
        APRIL: "Apr",
        MAY: "May",
        JUNE: "Jun",
        JULY: "Jul",
        AUGUST: "Aug",
        SEPTEMBER: "Sep",
        OCTOBER: "Oct",
        NOVEMBER: "Nov",
        DECEMBER: "Dec"
    };

    const value =
        String(
            month
        ).toUpperCase();

    return (
        map[value] ||
        String(
            month
        ).substring(
            0,
            3
        )
    );
}

function formatDayName(
    day
) {
    if (!day) {
        return "--";
    }

    const map = {
        MONDAY: "Mon",
        TUESDAY: "Tue",
        WEDNESDAY: "Wed",
        THURSDAY: "Thu",
        FRIDAY: "Fri",
        SATURDAY: "Sat",
        SUNDAY: "Sun"
    };

    const value =
        String(
            day
        ).toUpperCase();

    return (
        map[value] ||
        String(
            day
        ).substring(
            0,
            3
        )
    );
}

function getPerformanceClass(
    value
) {
    const performance =
        String(
            value || ""
        ).toLowerCase();

    if (
        performance ===
        "excellent"
    ) {
        return "good";
    }

    if (
        performance ===
        "good"
    ) {
        return "average";
    }

    return "low";
}

function normalize(
    value,
    max
) {
    if (
        !max ||
        max <= 0
    ) {
        return 0;
    }

    return (
        Number(value || 0) /
        max
    ) * 100;
}

function getChartColor(
    index
) {
    const colors = [
        "#0f766e",
        "#2563eb",
        "#7c3aed",
        "#f59e0b",
        "#dc2626",
        "#16a34a",
        "#0891b2",
        "#db2777"
    ];

    return colors[
        index %
        colors.length
    ];
}

function getTransparentColor(
    index
) {
    const colors = [
        "rgba(15,118,110,.75)",
        "rgba(37,99,235,.75)",
        "rgba(124,58,237,.75)",
        "rgba(245,158,11,.75)",
        "rgba(220,38,38,.75)",
        "rgba(22,163,74,.75)",
        "rgba(8,145,178,.75)",
        "rgba(219,39,119,.75)"
    ];

    return colors[
        index %
        colors.length
    ];
}

function hexToRgba(
    hex,
    alpha
) {
    const clean =
        hex.replace(
            "#",
            ""
        );

    const number =
        parseInt(
            clean,
            16
        );

    const r =
        (number >> 16) &
        255;

    const g =
        (number >> 8) &
        255;

    const b =
        number &
        255;

    return `
        rgba(
            ${r},
            ${g},
            ${b},
            ${alpha}
        )
    `;
}

function csvEscape(
    value
) {
    return `"${String(
        value ?? ""
    ).replaceAll(
        '"',
        '""'
    )}"`;
}

function escapeHtml(
    value
) {
    if (
        value === null ||
        value === undefined
    ) {
        return "";
    }

    return String(
        value
    )
        .replaceAll(
            "&",
            "&amp;"
        )
        .replaceAll(
            "<",
            "&lt;"
        )
        .replaceAll(
            ">",
            "&gt;"
        )
        .replaceAll(
            '"',
            "&quot;"
        )
        .replaceAll(
            "'",
            "&#039;"
        );
}

function showToast(
    message,
    type = "success",
    icon = "fa-circle-check"
) {
    const container =
        document.getElementById(
            "toastContainer"
        );

    if (!container) {
        return;
    }

    const toast =
        document.createElement(
            "div"
        );

    toast.className =
        `toast ${type}`;

    toast.innerHTML = `
        <i
            class="fa-solid ${icon}"
        ></i>

        <span>
            ${escapeHtml(
        message
    )}
        </span>
    `;

    container.appendChild(
        toast
    );

    setTimeout(
        () => {
            toast.classList.add(
                "hide"
            );

            setTimeout(
                () =>
                    toast.remove(),
                400
            );
        },
        3000
    );
}