# JavaTechnikiZaawansowane

## RMI
Program prezentuje w bardzo prosty sposób działania Zdalnego Wywoływania Metod (RMI)
Struktura wszystkich interfejsów została narzucona przez prowadzącego zajęcia.
- IBillboard
  * Służy do wyświetlania reklam
- IClient
  * Klient, który może składać zamówienia na reklamy
- IManager
  * Zarządza billboardami, przyjmuje zamówienia klientów
- Order
  * Reklama, która jest wyświetlana na billboardzie

### Konfiguracja
#### Argumenty VM:
1. -Djava.security.policy=file:{ścieżka_do_projektu}/security.policy 
2. -Djava.rmi.server.codebase=file:{ścieżka_do_projektu}
#### IBillboardImpl
Argumenty: PORT NAZWA_W_REJESTRZE MAKSYMALNA_ILOSC_REKLAM
#### IClientImpl
Argumenty: PORT NAZWA_W_REJESTRZE IMIE_KLIENTA
