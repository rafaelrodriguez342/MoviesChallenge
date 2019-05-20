# Movies Challenge

## Capas de la aplicación y responsabilidad de cada clase.
La aplicación está construida usando el patron arquitectual Model–View–Viewmodel, para lo cual la capa de lógica de negocio estará principalmente en los ViewModels.
Para las siguientes capas se usarán interfaces que desacoplarán la implementación específica de cada una de estas con el resto de la aplicación.

### Capa Base de datos SqLite 
Para la capa de base datos usamos la librería de Room y consta de las siguientes clases:
- `FilterEntity`: Model Object cuya responsabilidad es contener la configuración especifica de Room para guardar un Filtro en la bd.
- `FilterJoinVideoEntity`: Model Object cuya responsabilidad es contener la configuración especifica de Room para guardar la relación de un filtro con un video en la BD, esto porque la relación de un filtro y un video es de muchos a muchos.
- `VideoDataEntity`: Model Object cuya responsabilidad es contener la configuración especifica de Room para guardar un VideoData en la bd.
- `FiltersDao`: Objeto de Acceso de Datos con la responsabilidad de realizar las queries pertenecientes a la entidad de los Filtros(FilterEntity).
- `VideoDataDao`: Objeto de Acceso de Datos con la responsabilidad de realizar las queries pertenecientes a la entidad de la data de los videos(VideoDataEntity).
- `FilterJoinVideosDao`: Objeto de Acceso de Datos con la responsabilidad de realizar las queries pertenecientes a la relación entre filtros y videos (FilterJoinVideoEntity).
- `RoomVideoDataCacheRepository`: Implementación de VideoDataCacheRepository cuya responsabilidad es ser un repositorio el cual pueda proveer la data de los videos, y guardarla usando Room. 
- `VideosDatabase`: Clase con la responsabilidad de definir la configuración de Room para la base de datos.


### Capa para consumir los servicios REST.
Para la capa de servicios Rest usamos Retrofit y consta de las siguientes clases:
- `VideoDataApiClientRepository`: Implementación de VideoDataRepository cuya responsabilidad es ser un repositorio el cual pueda proveer la data de los videos usando para ello un Rest API. 
- `RetrofitVideoDataApiClientDao`: Objeto de Acceso de Datos de Retrofit con la responsabilidad de realizar las Http request de la data de los videos.
- `MovieVideoDataDTO`: Objeto de transferencia de datos responsable de almacenar la info del resultado del Http request de la data de un video de una Movie.
- `TvVideoDataDTO`: Objeto de transferencia de datos responsable de almacenar la info del resultado del Http request de la data de un video de una Serie.
- `VideoDataDTO`: Objeto de transferencia de datos responsable de almacenar la info del resultado del Http request de la data de un video, aquí ponemos lo que tiene en común las 2 clases anteriores.
- `VideoDataListResponseDTO`: Objeto de transferencia de datos responsable de almacenar la info del resultado del Http request.

## Capa de injeccion de dependencias
- `ApplicationClass`: El application class de la app, tiene la responsabilidad de inicializar componentes claves de la aplicación en este caso inicializamos y creamos el componente de Dagger.
- `AppComponent`: Componente de la App de Dagger con la responsabilidad de definir los Módulos que vamos a usar en la App.
- `ApplicationViewModelFactory`: Factory para injectar los ViewModels desde su creación.
- `ViewModelKey`: Annotacion usada para proveer los ViewModels al ApplicationViewModelFactory
- `CoreModule`: Modulo proveyendo las dependencias centrales de la app.
- `UIControllersModule`: Modulo para hacer binding a los UIControllers como las Activities y fragments para que sean injectadas.
- `ViewModelsModule`: Modulo para proveer los diferentes View Models que se usaran en la aplicación.

## Capa de Lógica de negocio.
- `Filter`: Model Object cuya responsabilidad es representar un filtro determinado compuesto por un Typo y Categoria de Videos.
- `VideoData`: Model Object cuya responsabilidad es representar la info de un Video.
- `VideoDataRepository`: Interfaz responsable de definir el contrato para los repositorios que provean la data de los videos.
- `VideoDataCacheRepository`: Interfaz responsable de definir el contrato para los repositorios que provean y persistan la data de los videos.
- `MainViewModel`: ViewModel responsable de manejar el UIController(MainActivity) administrando la data que este va a usar.

## Capa de UI Controllers
- `DetailsActivity`: UIController responsable manejar el UI del detail del video, para este caso no utilizamos un view model por que solo se hacen operaciones de UI para mostrar la data del video que ya está disponible en el intent de esta activity.
- `MainActivity`: UIController responsable de manejar el UI del activity de la galleria.
- `VideosAdapter`: Adapter responsable de configurar el UI y las vistas en base a los datos de un Video.

## Capa de Unit Tests
- `MainViewModelTest`: Clase responsable de almacenar los unit tests al MainViewModel.
- `SampleDataHelper`: Test helper para proveer la data proveniente de un JSON file.

## Capa de Android Tests
- `VideoDataRoomTest`: Clase responsable de verificar que las operaciones para leer e ingresar videos a la Room database se realicen correctamente.

## Principio de la responsabilidad Única y su Propósito.
El principio de la responsabilidad única consiste en que una clase debe tener solo una responsabilidad propiamente encapsulada y lo que contenga esta clase debe estar relacionado a esta responsabilidad, esto con el propósito de tener un código modular de alta cohesión que permita flexibilidad y transparencia a la hora de implementar nuevos cambios al código.
 ## Características de un código limpio.
Un código limpio debe ser fácil de leer, modular que las piezas puedan ser desarmadas y armadas como un rompecabezas sin mayores inconvenientes, debe tener buena cohesión es decir que lo que está dentro de cada clase esté relacionado a la responsabilidad que tiene esta, además de estar propiamente encapsulado para poder modificar solo las partes involucradas en un cambio sin tener que cambiar toda la aplicación, en lo posible las clases no deben ser largas, si se extiende demasiado una clase habría que analizar si está teniendo varias responsabilidades, un buen código además puede testearse fácil y por ello debe contar con unit tests- Android tests según se requieran para verificar el correcto funcionamiento de un requerimiento, también un buen código debe evitar tener código duplicado en la aplicación para evitar cambiar el mismo código varias veces, un buen código debe evitar tener procesos innecesarios o redundantes, y debe tener comentarios en los procesos que no sean tan obvios. 
