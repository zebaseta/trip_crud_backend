En este trabajo se simula una consola de administración de una empresa viajes, dónde puede generarse altas de aeropuertos, aerolíneas y viajes. 
Los viajes pueden componerse un por un conjunto de vuelos de ida y un conjunto de vuelos de vuelta, y debe permitirse la carga de este listado de vuelos sin discriminar cuáles son vuelos de ida y de vuelta. Al solicitarse posteriormente un listado de viajes deben los mismos visualizarse de forma discriminada. 

* Repositorio front: https://github.com/zebaseta/trip_crud_frontend
* Api de swagger backend: https://springboot-otravo-trips.herokuapp.com/otravo/v1/swagger-ui.html (heroku)
* Frontend: https://master.dcj0k2xihltxq.amplifyapp.com/ (aws)
* Uri de healtcheck (para monitorear mediante un hearthbeat por ejemplo): https://springboot-otravo-trips.herokuapp.com/otravo/v1/actuator/health/custom
* Papertrail: https://my.papertrailapp.com/systems/springboot-otravo-trips/events?focus=1339634665387204618 (logs centralizados)

Funcionalidades destacadas en el front:
- Login de usuario
- ABM de aerolinea
- ABM de aeropuerto
- Alta de  viaje
- Baja de  viaje
- Ver todos los viajes
- Ver viajes por usuario
- Cache de token, y datos de aeropuertos y aerlonias

Funcionalidades destacadas en el back
- Abm  aerolínea
- Abm aeropuerto
- Ver todas las aerolineas
- Ver todos aeropuertos
- Alta y baja viaje
- Cache de aeropuertos y aerolineas
- Trazabilidad de requests generado tokens por cada request y generando logs con los correspondientes tokens de request
- Seguridad mediante JWT, con un token que expira luego de 1 hora.


Modelo entidad relación
![image](https://user-images.githubusercontent.com/48572502/121543293-2e809200-c9df-11eb-9a83-d1315e0cd089.png)
