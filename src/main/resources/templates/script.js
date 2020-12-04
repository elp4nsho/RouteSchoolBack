var i = 0;
agregarNino = () => {
    let este = document.querySelector("#addKid");
    let divTitle = document.createElement("div");
    divTitle.addEventListener("click", eliminar)
    divTitle.id = "nuevoNino:" + i


    divTitle.className = "title"
    divTitle.style.display = "flex";
    divTitle.style.justifyContent = "space-between";
    let icon = document.createElement("i");
    icon.className = "fas fa-times fa-lg"
    icon.style.color = "#ff3113"

    let h2 = document.createElement("h2");
    h2.innerText = "Registro de niño"
    divTitle.appendChild(h2)
    divTitle.appendChild(icon)

    este.parentNode.insertBefore(divTitle, este.previousSibling);

    let dirA = document.getElementById("direccionApoderado").value

    este.parentNode.insertBefore(unInput("Rut nino", "rutNino" + i), este.previousSibling);
    este.parentNode.insertBefore(unInput("Nombre niño", "nombreNino" + i), este.previousSibling);
    este.parentNode.insertBefore(unInput("Apellido paterno", "apellidoPNino" + i), este.previousSibling);
    este.parentNode.insertBefore(unInput("Apellido materno", "apellidoMNino" + i), este.previousSibling);
    let divDireccion = document.createElement("div");
    let direccionInput = unInput("Direccion", "direccionNino" + i);
    divDireccion.append(direccionInput)
    let botonDir = document.createElement("button");
    botonDir.innerText = "buscar"
    botonDir.id = "btnBuscar:" + i
    botonDir.addEventListener("click", geocodeAddress)
    divDireccion.append(botonDir)
    divDireccion.style.display = "flex"
    divDireccion.style.justifyContent = "space-between"
    divDireccion.style.alignItems = "baseline"
    divDireccion.className = "title"
    este.parentNode.insertBefore(divDireccion, este.previousSibling);



    console.log(dirA)

    document.getElementById("direccionNino" + i).value = dirA

    let elMapa = document.createElement("div");
    elMapa.id = "nuevoMapa:" + i;
    elMapa.className = "map title";

    este.parentNode.insertBefore(elMapa, este.previousSibling);

    let map = iniciarMapa("nuevoMapa:" + i);


    let marker = new google.maps.Marker({
        map: map,
        position: {lat: -33.436958, lng: -70.634441},
    });

    map.addListener("click", (mapsMouseEvent) => {
        // Close the current InfoWindow.
        let position = mapsMouseEvent.latLng
        console.log(map)
        geocodeLatLng(geocoder, map, position, map.id)
        marker.setPosition(position)

    });


    /*   <div class="title">
               <i class="fas fa-pencil-alt"></i>
               <h2>Registro de apoderado</h2>
           </div>*/
    let selectColegios = document.createElement("select")
    selectColegios.id = "selectNino" + i


    for (let c of colegios) {
        let option = document.createElement("option");
        option.innerText = c["nombre"];
        option.value = c["idColegio"];
        selectColegios.appendChild(option);
    }


    let labelColegios = document.createElement("label")
    labelColegios.innerText = "Colegio"
    labelColegios.id = "lblColegio"+i
    este.parentNode.insertBefore(labelColegios, este.previousSibling);
    este.parentNode.insertBefore(selectColegios, este.previousSibling);

    este.parentNode.insertBefore(unInput("Edad", "edadNino" + i), este.previousSibling);

    let selectCursos = document.createElement("select");
    selectCursos.id = "selectCursos"+i;
    let cursos = ["pre-kinder","kinder","primero basico","segundo basico","tercero basico","cuarto basico","quinto basico","sexto basico","septimo basico","octavo basico"
        ,"primero medio","segundo medio","tercero medio","cuarto medio","otros"]
    for(let cc of cursos){
        let option = document.createElement("option");
        option.innerText = cc;
        option.value = cc;
        selectCursos.appendChild(option);
    }
    let labelCursos = document.createElement("label")
    labelCursos.innerText = "Curso"
    labelCursos.id = "lblCurso"+i
    este.parentNode.insertBefore(labelCursos, este.previousSibling);
    este.parentNode.insertBefore(selectCursos, este.previousSibling);


    let selectModalidad = document.createElement("select");
    selectModalidad.id = "selectModalidad"+i;
    let modalidades = ["Solo mañana","Solo tarde","Mañana y tarde"]
    for(let m of modalidades){
        let option = document.createElement("option");
        option.innerText = m;
        option.value = m == "Solo mañana"?1:m == "Solo tarde" ?2:"Mañana y tarde"?3:0;
        selectModalidad.appendChild(option);
    }
    let labelModalidad = document.createElement("label")
    labelModalidad.innerText = "Modalidad de retiro"
    labelModalidad.id = "lblModalidad"+i
    este.parentNode.insertBefore(labelModalidad, este.previousSibling);
    este.parentNode.insertBefore(selectModalidad, este.previousSibling);






    nino = {
        idDiv: "nuevoMapa" + i,
        i,
        dirValue: {lat: -33.436958, lng: -70.634441},
        map,
        marker,
    };

    ninos.push(nino);
    console.log(nino);
    i++;


};
