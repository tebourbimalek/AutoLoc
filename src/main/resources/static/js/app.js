const API_URL = "/vehicules";

const tbody = document.getElementById("tbody");
const skeleton = document.getElementById("skeleton");
const emptyState = document.getElementById("empty-state");
const tableWrapper = document.getElementById("table-wrapper");

const searchInput = document.getElementById("search");
const filterBtns = document.querySelectorAll(".segmented__btn");
const filterCategorie = document.getElementById("filter-categorie");
const filterStatut = document.getElementById("filter-statut");

const countTotal = document.getElementById("count-total");
const countDispo = document.getElementById("count-dispo");
const countLouee = document.getElementById("count-loue");
const countMaint = document.getElementById("count-maint");

const btnRefresh = document.getElementById("btn-refresh");
const btnOpenForm = document.getElementById("btn-open-form");
const btnEmptyAdd = document.getElementById("btn-empty-add");
const btnDeleteAll = document.getElementById("btn-delete-all");

const modalAdd = document.getElementById("modal-add");
const formAdd = document.getElementById("form-add");
const btnCloseForm = document.getElementById("btn-close-form");
const btnCancelForm = document.getElementById("btn-cancel-form");

const toastEl = document.getElementById("toast");
let toastTimeout;

let vehicles = [];
let currentFilterDispo = "all";
let searchTerm = "";

function showToast(message, type = "info") {
    clearTimeout(toastTimeout);
    toastEl.textContent = message;
    toastEl.classList.remove("hidden", "toast--success", "toast--error");
    if (type === "success") toastEl.classList.add("toast--success");
    if (type === "error") toastEl.classList.add("toast--error");
    requestAnimationFrame(() => toastEl.classList.add("show"));
    toastTimeout = setTimeout(() => {
        toastEl.classList.remove("show");
        setTimeout(() => toastEl.classList.add("hidden"), 160);
    }, 2600);
}

function money(v) {
    const n = typeof v === "number" ? v : Number(v);
    if (Number.isNaN(n)) return "—";
    return new Intl.NumberFormat("fr-FR", {
        style: "currency",
        currency: "TND",
        minimumFractionDigits: 2,
    }).format(n);
}

function badgeForDisponible(d) {
    return d
        ? '<span class="badge badge--success"><i class="bi bi-check-circle"></i> Disponible</span>'
        : '<span class="badge badge--danger"><i class="bi bi-x-circle"></i> Non disponible</span>';
}

function badgeForStatut(s) {
    switch (s) {
        case "DISPONIBLE":
            return '<span class="badge badge--success">DISPONIBLE</span>';
        case "LOUE":
            return '<span class="badge badge--warning">LOUÉ</span>';
        case "MAINTENANCE":
            return '<span class="badge badge--neutral">MAINTENANCE</span>';
        default:
            return `<span class="badge badge--neutral">${s ?? "—"}</span>`;
    }
}

function badgeForCategorie(c) {
    switch (c) {
        case "CITADINE":
            return '<span class="badge badge--neutral">Citadine</span>';
        case "BERLINE":
            return '<span class="badge badge--neutral">Berline</span>';
        case "SUV":
            return '<span class="badge badge--neutral">SUV</span>';
        case "MONOSPACE":
            return '<span class="badge badge--neutral">Monospace</span>';
        case "UTILITAIRE":
            return '<span class="badge badge--neutral">Utilitaire</span>';
        default:
            return c ? `<span class="badge badge--neutral">${c}</span>` : "—";
    }
}

function setLoading(isLoading) {
    skeleton.classList.toggle("hidden", !isLoading);
    tableWrapper.classList.toggle("hidden", isLoading);
}

function applyFilters() {
    const term = searchTerm.trim().toLowerCase();
    const cat = filterCategorie.value;
    const st = filterStatut.value;

    let filtered = [...vehicles];

    if (currentFilterDispo !== "all") {
        const want = currentFilterDispo === "true";
        filtered = filtered.filter((v) => Boolean(v.disponible) === want);
    }

    if (term) {
        filtered = filtered.filter((v) => {
            const m = [v.marque, v.modele, v.immatriculation, v.couleur]
                .filter(Boolean)
                .join(" ")
                .toLowerCase();
            return m.includes(term);
        });
    }

    if (cat !== "all") {
        filtered = filtered.filter((v) => (v.categorie || "") === cat);
    }

    if (st !== "all") {
        filtered = filtered.filter((v) => (v.statut || "") === st);
    }

    renderTable(filtered);
}

function updateCounts(list) {
    countTotal.textContent = String(list.length);
    countDispo.textContent = String(list.filter((v) => v.disponible).length);
    countLouee.textContent = String(list.filter((v) => v.statut === "LOUE").length);
    countMaint.textContent = String(list.filter((v) => v.statut === "MAINTENANCE").length);
}

function renderTable(list) {
    updateCounts(vehicles);

    if (list.length === 0) {
        tbody.innerHTML = "";
        emptyState.classList.remove("hidden");
        return;
    }
    emptyState.classList.add("hidden");

    const rows = list
        .slice()
        .sort((a, b) => {
            const idA = a.idVehicule ?? 0;
            const idB = b.idVehicule ?? 0;
            return idB - idA;
        })
        .map((v) => {
            const id = v.idVehicule ?? "—";
            return `
        <tr>
          <td><strong>#${id}</strong></td>
          <td>${v.marque ?? "—"}</td>
          <td>${v.modele ?? "—"}</td>
          <td><code>${v.immatriculation ?? "—"}</code></td>
          <td>${v.couleur ?? "—"}</td>
          <td>${money(v.prixJour)}</td>
          <td>${badgeForDisponible(v.disponible)}</td>
          <td>${badgeForStatut(v.statut)}</td>
          <td>${badgeForCategorie(v.categorie)}</td>
          <td style="text-align:right; white-space:nowrap;">
            <button class="btn btn--danger btn--ghost btn--icon btn-delete" data-id="${id}" title="Supprimer ce véhicule">
              <i class="bi bi-trash3"></i>
            </button>
          </td>
        </tr>
      `;
        })
        .join("");

    tbody.innerHTML = rows;

    document.querySelectorAll(".btn-delete").forEach((btn) => {
        btn.addEventListener("click", async () => {
            const raw = btn.dataset.id;
            const id = Number(raw);
            if (!Number.isFinite(id)) return;
            if (!confirm(`Supprimer le véhicule #${id} ?`)) return;
            try {
                const res = await fetch(`${API_URL}/${id}`, {method: "DELETE"});
                if (res.ok || res.status === 204) {
                    vehicles = vehicles.filter((v) => v.idVehicule !== id);
                    applyFilters();
                    showToast(`Véhicule #${id} supprimé`, "success");
                } else {
                    showToast(`Erreur suppression (${res.status})`, "error");
                }
            } catch (e) {
                showToast("Erreur réseau lors de la suppression", "error");
            }
        });
    });
}

async function fetchVehicles() {
    setLoading(true);
    try {
        const res = await fetch(API_URL, {headers: {"Accept": "application/json"}});
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const data = await res.json();
        vehicles = Array.isArray(data) ? data : [];
        applyFilters();
        showToast(`${vehicles.length} véhicule(s) chargé(s)`, "success");
    } catch (err) {
        console.error(err);
        emptyState.classList.remove("hidden");
        tbody.innerHTML = "";
        showToast("Impossible de charger /vehicules", "error");
    } finally {
        setLoading(false);
    }
}

filterBtns.forEach((btn) => {
    btn.addEventListener("click", () => {
        filterBtns.forEach((b) => b.classList.remove("active"));
        btn.classList.add("active");
        currentFilterDispo = btn.dataset.filter;
        applyFilters();
    });
});

searchInput.addEventListener("input", () => {
    searchTerm = searchInput.value;
    applyFilters();
});

filterCategorie.addEventListener("change", applyFilters);
filterStatut.addEventListener("change", applyFilters);

btnRefresh.addEventListener("click", fetchVehicles);
btnEmptyAdd.addEventListener("click", () => modalAdd.showModal());
btnOpenForm.addEventListener("click", () => modalAdd.showModal());

btnCloseForm.addEventListener("click", () => modalAdd.close());
btnCancelForm.addEventListener("click", () => modalAdd.close());
modalAdd.addEventListener("close", () => formAdd.reset());

formAdd.addEventListener("submit", async (e) => {
    e.preventDefault();
    const fd = new FormData(formAdd);
    const payload = {
        marque: fd.get("marque")?.trim(),
        modele: fd.get("modele")?.trim(),
        immatriculation: fd.get("immatriculation")?.trim(),
        couleur: fd.get("couleur")?.trim() || null,
        prixJour: fd.get("prixJour") === "" ? null : Number(fd.get("prixJour")),
        disponible: fd.get("disponible") === "true",
        statut: fd.get("statut"),
        categorie: fd.get("categorie"),
    };

    try {
        const res = await fetch(API_URL, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload),
        });
        if (res.status !== 201) {
            const txt = await res.text().catch(() => "");
            throw new Error(`HTTP ${res.status} ${txt}`);
        }
        const created = await res.json();
        vehicles.unshift(created);
        modalAdd.close();
        formAdd.reset();
        applyFilters();
        showToast(`Véhicule #${created.idVehicule} créé`, "success");
    } catch (err) {
        console.error(err);
        showToast("Erreur à la création du véhicule", "error");
    }
});

btnDeleteAll.addEventListener("click", async () => {
    if (vehicles.length === 0) return;
    if (!confirm("Supprimer TOUS les véhicules ? Cette action est irréversible.")) return;
    try {
        const res = await fetch(API_URL, {method: "DELETE"});
        if (res.ok || res.status === 204) {
            vehicles = [];
            applyFilters();
            showToast("Tous les véhicules ont été supprimés", "success");
        } else {
            showToast(`Erreur (${res.status})`, "error");
        }
    } catch (e) {
        showToast("Erreur réseau", "error");
    }
});

fetchVehicles();