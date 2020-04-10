# BatSoup

BatSoup to aplikacja mająca zamiar informować użytkowników o lokalizacji najbliższych osób zarażonych wirusem COVID-19


# Widok aplikacji
![alt_text](https://i.imgur.com/NzOe7Ebm.png) ![alt_text](https://i.imgur.com/2NMPm8Ym.png)


# Co aplikacja oferuje
  - Przyjazny dla użytkownika interfejs
  - Możliwość dodania informacji o swoim statusie oraz sprawdzenia statusu innych osób w pobliżu
  - Posiadanie własnego konta

# Użyte technologie
  - Java Spring- backendowy framework Javy w której napisane jest API, z którym komunikuje się Klient (aplikacja)
  - Retrofit - technologia umożliwiająca w łatwy sposób dokonywać zapytania do API z poziomu klienta
  - MongoDB Atlas - chmura na której przechowuję dane o użytkownikach, pole location zawiera index GeoSpatial, który umożliwia mi wykonywanie zapytań na obiektach geometrycznych
  - JWT (JSON Web Token) - Token w postaci JSON'a umozliwiający autoryzację użytkownika 
  - Fragmenty - utworzono dwa Fragmenty (logowanie i rejestracja), które się wzajemnie zastępują w LoginActivity, oraz dwa fragmenty w MainActivity, jeden do monitorowania statusu osób w pobliżu, drugi do sprawdzenia swojego statusu
  - Material Design - technologia, która zawiera już gotowe komponenty dla widoku
# Planowane mechanizmy
- Informacja w postaci sygnału dźwiękowego, jeżeli użytkownik znajduje się w pobliżu osoby zarażonej
- Notyfikacja z aplikacji w przypadku spotkania się w pobliżu osoby zarażonej

# Wzorce projektowe
- Repository
- Builder

# Wzorce architektoniczne
- MVC (Model - View - Controller)- backend
- MVVM (Model - View - ViewModel)- frontend 
