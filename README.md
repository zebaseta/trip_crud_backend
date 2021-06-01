El presente es un repositorio donde se simula una consola de administración de una empresa viajes, dónde puede generarse altas de aeropuertos, aerolíneas y viajes. 
Los viajes pueden componerse un por un conjunto de vuelos de ida y un conjunto de vuelos de vuelta, y debe permitirse la carga de este listado de vuelos sin discriminar cuáles son vuelos de ida y de vuelta. Al solicitarse posteriormente un listado de viajes deben los mismos visualizarse de forma discriminada. 

* Repositorio front: https://github.com/zebaseta/trip_crud_frontend
* Api de swagger backend: https://springboot-otravo-trips.herokuapp.com/otravo/v1/swagger-ui.html
* Frontend: https://master.dcj0k2xihltxq.amplifyapp.com/ 
* Uri de healtcheck (para monitorear mediante un hearthbeat por ejemplo): https://springboot-otravo-* trips.herokuapp.com/otravo/v1/actuator/health/custom

Funcionalidades destacadas en el front:
- Log de usuario
- Alta de aerolinea
- Alta de aeropuerto
- Alta de  viaje
- Ver todos los viajes
- Ver viajes por usuario

Funcionalidades destacadas en el back
- Abm  aerolínea
- Abm aeropuerto
- Ver todas las aerolineas
- Ver todos aeropuertos
- Alta viaje
- Trazabilidad de requests generado tokens por cada request y generando logs con los correspondientes tokens de request
- Seguridad mediante JWT, con un token que expira luego de 1 hora.
