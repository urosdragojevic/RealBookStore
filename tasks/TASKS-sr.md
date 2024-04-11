# Razvoj bezbednog softvera projekat 2023/2024

## Sadržaj
1.	[Uvod](#uvod)
2.	[Primena alata za statičku analizu](#primena-alata-za-statičku-analizu)
3.	[SQL injection i Cross-site scripting](#sql-injection-i-cross-site-scripting)
4.	[Cross-site request forgery](#cross-site-request-forgery)
5.	[Implementacija autorizacije](#implementacija-autorizacije)
6.	[DevOps](#devops)
7.	[Predaja projekta](#predaja-projekta)
8.	[Odbrana projekta](#odbrana-projekta)

## Uvod

Projekat se izvodi na aplikaciji Real Book Store koja pruža mogućnosti pretrage, ocenjivanja i komentarisanja
knjiga.

Projekat je potrebno skinuti sa sledećeg linka: https://github.com/urosdragojevic/RealBookStore

Aplikacija RealBookStore omogućava sledeće:

- Pregled i pretragu knjiga (Slika 1.1).
- Dodavanje nove knjige (Slika 1.2).
- Detaljan pregleda knjige kao i komentarisanje i ocenjivanje knjige (Slika 1.3).
- Pregled korisnika aplikacije (Slika 1.4).
- Detaljan pregled podataka korisnika (Slika 1.5).


![books.html](C:\Users\drru\Projects\RealBookStore\tasks\img\books.png)

Slika 1.1 Pregled i pretraga knjiga (`books.html`)

---

![create-form.html](C:\Users\drru\Projects\RealBookStore\tasks\img\add_book.png)

Slika 1.2 Dodavanje nove knjige (`create-form.html`)

---

![book.html](C:\Users\drru\Projects\RealBookStore\tasks\img\book_details.png) 

Slika 1.3 Detaljan pregleda knjige kao i komentarisanje i ocenjivanje knjige (`book.html`)

---

![persons.html](C:\Users\drru\Projects\RealBookStore\tasks\img\users.png)

Slika 1.4 Pregled korisnika aplikacije (`persons.html`)

---

![person.html](C:\Users\drru\Projects\RealBookStore\tasks\img\profile.png)

Slika 1.5 Detaljan pregled podataka korisnika (`person.html`)

---

U nastavku teksta su definisane stavke koje je potrebno uraditi u okviru projekta.

## Primena alata za statičku analizu
### Broj poena: 3

Pokrenuti alat za statičku analizu SonarQube i sastaviti izveštaj koji predajete sa projektom.
Analizirati sve tačke (ukoliko postoje) u _Vulnerability_ i _Security Hotspot_ sekcijama alata.

Za svaku stavku je potrebno:

- Obeležiti je kao **true positive** (confirm) ili **false positive uz obrazloženje**.
- Uraditi screenshot i priložiti uz rešenje projekta.

Potrebno je da izveštaj bude pregledan.

> Predlog: Preporuka je da se izveštaj preda u vidu Excel tabele sa screenshot-ovima (naravno, prihvataju se ostala rešenja).
> U svakom redu Excel tabele je potrebno da se navede jedan _Vulnerability_ ili _Security Hotspot_ i da se navede ime slike za naveden sigurnosni propust/ranjivost.

## SQL injection i Cross-site scripting
### Broj poena: 15

#### Napad

Na stranici na kojoj se pregleda pojedinačna knjiga (http://localhost:8080/books/{id}, Slika 1.3) nalazi se forma za ostavljanje komentara.

Proveriti da li preko ove forme može da se izvrši neka vrsta XSS ili SQLi napada.

U slučaju da može, iskoristiti odgovarajuću ranjivost da ubacite novog korisnika u bazu.
Voditi računa, novog korisnika je potrebno ubaciti u tabelu person, a ne u tabelu user u bazi.

Stranica `persons.html` (Slika 1.4) ima propust u zaštiti od XSS napada, pa je konačni zadatak da ubačeni korisnik, kao neki od svojih atributa ima zlonamernu JavaScript skriptu koja će na konzolni izlaz da ispiše vrednost kolačića sesije korisnika.
Ranjivost se aktivira prilikom pretrage korisnika na stranici `persons.html` (Slika 1.4).

Obavezno je dokumentovati ovaj napad i poslati ga sa projektnim zadatkom.


#### Odbrana

Neophodno je zaštititi se od prethodno opisanog napada tako da ne može da se izvrši napad preko forme za ostavljanje komentara.
Odnosno, neophodno izvršiti zaštitu od SQLi napada i/ili od XSS napada.

Takođe, na stranici `persons.html` (Slika 1.4) neophodno je popraviti ranjivosti koje omogućuju XSS napad.
_Popravka ranjivosti ne sme da naruši ili promeni funkcionalnost aplikacije._

> Napomena: Nije potrebno zaštititi celu aplikaciju od SQLi i XSS napada, već samo na mestima na kojima je uočen propust u ovoj sekciji (sekciji 3). 

##	Cross-site request forgery
### Broj poena: 7

#### Napad

Aplikacija nema zaštitu protiv CSRF napada.

Neophodno je demonstrirati napad na sledeći način: koristeći aplikaciju koja simulira napad (folder `csrf-exploit` unutar projekta) treba napraviti skriptu tj. poziv ka endpointu `/update-person` unutar `PersonsController.java` klase.
Napad treba da promeni lične podatke korisnika sa `id = 1`, tako da je `firstName = “Batman”` i `lastName = “Dark Knight”`.

_Obavezno dokumentovati napad i predati ga sa projektom._

#### Odbrana

Neophodno je implementirati zaštitu od CSRF napada korišćenjem tokena.

Odbranu je potrebno primeniti samo na gorenavedeni endpoint. Popravka ranjivosti ne sme da naruši ili promeni funkcionalnost aplikacije.

> Za dohvatanje vrednosti CSRF tokena možete iskoristiti `HttpSession httpSession` objekat u metodi kontrolera.
> Token generiše i čuva u sesiji korisnika implementacija koju možete naći u `CsrfHttpSessionListener.java` klasi.

## Implementacija autorizacije
### Broj poena: 10

Implementirajte matricu permisija kako je definisano u tabeli (Tabela 5.1) koristeći Spring Security i
Thymeleaf koncepte koje smo učili na vežbama.

Tabela 5.1 Tabela permisija

| Permisija         | ADMIN | MANAGER                | REVIEWER              |
|-------------------|-------|------------------------|-----------------------|
| ADD_COMMENT	      | *	    | *	                     | *                     |
| VIEW_BOOKS_LIST	 | *	    | *	                     | *                     |
| CREATE_BOOK	     | *	    | *	                     | X                     |
| VIEW_PERSONS_LIST | *	    | *	                     | X                     |
| VIEW_PERSON	      | *	    | X                      | X                     |	
| UPDATE_PERSON	    | *	    | * - pogledaj napomenu	 | * - pogledaj napomenu |
| VIEW_MY_PROFILE	  | *	    | *	                     | *                     |
| RATE_BOOK	       | *	    | *                      | *                     |

Postavite da korisnik **tom** ima rolu _ADMIN_, **toelover** rolu _MANAGER_ i korisnici **bruce** i **sam** imaju rolu _REVIEWER_. Napravite u bazi nedostajuću rolu i nedostajuće permisije. 

U nastavku se nalaze kratki opisi permisija:

| Permisija         | Opis                                                                             |
|-------------------|----------------------------------------------------------------------------------|
| ADD_COMMENT	      | Dozvoljava korisniku da doda komentar.                                           |
| VIEW_BOOKS_LIST	 | Dozvoljava korisniku da vidi i pretraži listu knjiga.                            |
| CREATE_BOOK	     | Dozvoljava korisniku da unese novu knjigu.                                       |
| VIEW_PERSONS_LIST | Dozvoljava korisniku da vidi i pretraži listu korisnika.                         |
| VIEW_PERSON	      | Dozvoljava korisniku da vidi detalje osobe.                                      |
| UPDATE_PERSON     | Dozvoljava korisniku da promeni detalje osobe (isto važi za brisanje korisnika). |
| VIEW_MY_PROFILE   | Dozvoljava pregled sopstvenog profila.                                           |
| RATE_BOOK	       | Dozvoljava da korisnik oceni knjigu.                                             |


> Napomena: Rolama _MANAGER_ i _REVIEWER_ je dozvoljena promena isključivo svojih sopstvenih podataka.
> Koristite pomoćnu metodu `getPrincipal` klase `Authentication` kako biste dohvatili trenutno prijavljenog korisnika.

## DevOps
### Broj poena: 5

#### Rukovanje izuzecima i logovanje

Uvesti obradu i logovanje svih izuzetaka u aplikaciji.
Dodati logove koji bi bili korisni u analizi u slučaju napada.

Ocenjivaće se:

- Izbor mesta u kodu gde je napravljen unos u log.
- Izbor log kategorije prema principima koji su predstavljeni na vežbama.
- Relevantnost opisa i podataka koji se nalaze u log poruci.

#### Auditing

Uvesti auditing aplikaciji.

Ocenjivaće se:
- Implementacija auditing-a.
- Izbor korisničkih akcija za koje se vrši audit prema principima koji su predstavljenim na vežbama.
- Tačnost audit-a u pružanju sigurnosne usluge neporecivosti (_non-repudiation_).

> Napomena: Za lakšu implementaciju auditing dela zadatka mogu se koristiti metode iz klase `AuditLogger`.
> Pri obradi izuzetaka treba uzeti u obzir da li će se korisnikovo iskustvo poboljšati ukoliko mu  se prikaže smislena poruka na korisničkom interfejsu.
> Ovaj deo se neće ocenjivati.
 
## Predaja projekta

1. Forkujte ovaj repozitorijum na svoj GitHub nalog.
2. Rešenja zadataka commitujte i pushujte na svoj repo. 
3. `main` grana vašeg repozitorijuma treba da bude u takvom stanju da su rešeni svi zadaci (implementirane su sve potrebne zaštite).
4. Izveštaje (screenshotove napada i Sonarqube izveštaj) commitujte u folder `report`

> U folderu `report` se nalaze primeri izveštaja koje možete koristiti kao inspiraciju.
> Možete koristiti taj format ili bilo koji drugi, samo da je razumljiv i čitljiv.

## Odbrana projekta

Odbrana projekta će biti održana u unapred dogovorenom terminu.

Svaki student će _samostalno_ braniti svoj projekat.
