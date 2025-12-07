// ==============================
//  Función para timeout usando fetch
// ==============================
async function fetchWithTimeout(resource, options = {}) {
    const { timeout = 15000 } = options; // 15 segundos por si tarda
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), timeout);

    try {
        const resp = await fetch(resource, {
            ...options,
            signal: controller.signal
        });
        clearTimeout(timeoutId);
        return resp;
    } catch (err) {
        clearTimeout(timeoutId);
        throw err;
    }
}

// ==============================
//  Consultar por ID
// ==============================
async function consultar() {
    const id = document.getElementById("txtId").value;
    const url = `${baseUrl}/api/student?id=${id}&tree=1`;

    document.getElementById("zona-tablas").innerHTML =
        "<p>Cargando datos...</p>";

    try {
        const resp = await fetchWithTimeout(url, { timeout: 20000 });
        if (!resp.ok) throw new Error("Error al consultar API");

        const data = await resp.json();
        renderTablas(data);

    } catch (err) {
        document.getElementById("zona-tablas").innerHTML =
            `<p style='color:red'>Error: ${err.message}</p>`;
    }
}

// ==============================
//  Render dinámico del JSON
// ==============================
function renderTablas(data) {

    const student = data.student ?? {};
    const academico = data.academico ?? {};
    const materias = data.materias ?? [];
    const preferencias = data.preferencias ?? {};
    const notificaciones = data.notificaciones ?? preferencias.notificaciones ?? {};

    const actividades = data.actividadesExtracurriculares
        ?? preferencias.actividadesExtracurriculares
        ?? [];

    let html = `
    <div class="card shadow-sm mt-3">
        <div class="card-body">

            <h4 class="mb-3">📌 Datos del Estudiante</h4>
            <p><b>Nombre:</b> ${student.nombreCompleto ?? "No disponible"}</p>
            <p><b>Edad:</b> ${student.edad ?? "No disponible"}</p>
            <p><b>Correo:</b> ${student.correo ?? "No disponible"}</p>
            <p><b>Teléfono:</b> ${student.telefono ?? "No disponible"}</p>
            <p><b>Ciudad:</b> ${student.ciudadResidencia ?? "No disponible"}</p>

            <hr>

            <h4 class="mb-3">🎓 Información Académica</h4>
            <p><b>Programa:</b> ${academico.programa ?? "No disponible"}</p>
            <p><b>Semestre:</b> ${academico.semestreActual ?? "-"}</p>
            <p><b>Promedio:</b> ${academico.promedioAcumulado ?? "-"}</p>

            <hr>

            <h4 class="mb-3">📚 Materias</h4>
            <ul class="list-group">
                ${
        materias.length > 0
            ? materias.map(m => `
                        <li class="list-group-item d-flex justify-content-between">
                            <span>${m.nombre}</span>
                            <span class="badge bg-primary">${m.estado}</span>
                        </li>
                    `).join("")
            : `<li class="list-group-item">No tiene materias registradas</li>`
    }
            </ul>

            <hr>

            <h4 class="mt-3">⭐ Actividades Extracurriculares</h4>
            <ul>
                ${
        actividades.length > 0
            ? actividades.map(a => `<li>${a}</li>`).join("")
            : `<li>No registradas</li>`
    }
            </ul>

            <hr>

            <h4 class="mt-3">🔔 Notificaciones</h4>
            <p><b>Email:</b> ${notificaciones.email ? "✔️" : "❌"}</p>
            <p><b>SMS:</b> ${notificaciones.sms ? "✔️" : "❌"}</p>
            <p><b>App:</b> ${notificaciones.app ? "✔️" : "❌"}</p>

        </div>
    </div>
    `;

    document.getElementById("zona-tablas").innerHTML = html;
}


// ==============================
//  Consulta por defecto ID = 1
// ==============================
window.onload = () => consultar();