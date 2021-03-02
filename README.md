# VideosDB

## Enunt

Este vorba despre o aplicatie de filme asemanatoare cu **Netflix**, care contine 
**filme**, **seriale** si **utilizatori**, cei din urma putand sa efectueze divere actiuni,
**comenzi, query-uri, recomandari**. Mai multe detalii despre proiect se pot gasi [aici](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema).
In cele ce urmeaza, voi explica functionalitatea aplicatiei si modul de implementare
a actiunilor.

## Command
Din lista de useri, il gasesc pe acela care are username-ul cel din input-ul
comenzii. Fiecare metoda din aceasta sectiune, aflate in clasa UserInputData,
imi formeaza un mesaj care va fi scris in fisierele de output.

### Favorite
Verific daca video-ul se afla deja in lista de favorite a user-ului respectiv,
caz in care nu fac nimic. Daca nu, verific daca acesta a fost vizionat inainte
de user. Daca nu, atunci il adaug in lista de favorite video a user-ului.
Altfel nu il adaug.

### View
Verific daca video-ul a fost deja vizionat, caz in care ii cresc numarul de
vizualizari. Altfel incrementez numarul de vizualizari din map-ul history.

### Rating
Construiesc un map, video -> rating. Video-ul este de forma:
 *nume video + season + numar sezon*. In cazul in care video-ul este *film*,
 atunci voi considera numarul sezonului 0.

Daca video-ul pe care se doreste a da rating, a fost vizualizat si nu se afla
in map-ul de rating (nu a primit rating inainte), atunci adaug noua intrare in
map. Altfel ignor comanda.

## Query
Folosesc mai mult metode de tipul *sort lista by criteriu*. Sortarea este de 2
tipuri, *asc* sau *desc*, parametru dat in fiecare metoda.

### Actors

#### Average
Formez o noua lista de video-uri, formata din filmele si serialele date in
input. Pentru fiecare video din aceasta lista, calculez rating-ul pe baza
listei de useri.

Calculez *rating-ul unui actor* pe baza unei lista de video-uri. Parcurg
lista, iar daca video-ul curent se afla in lista de filme in care a jucat, iau
in considerare video-ul (cresc suma rating-urilor si numarul acestora).

Fac acest lucru pentru fiecare actor din lista de actori si ii sortez dupa
rating-ul obtinut.

#### Awards
Convertesc lista de award-uri primita ca parametru din String in Enum si
gasesc actorii care au toate aceste award-uri. Parcurg lista de actori si
formez o noua lista de actori, daca actorul curent are toate award-urile
cerute.

Pentru fiecare actor, numar cate award-uri are in total si sortez lista
obtinuta dupa acest numar.

#### Filter Description
Iau lista de cuvinte pe care trebuie sa o contina un actor. Pentru a verifica
daca un actor contine in descrierea carierei toate aceste cuvinte procedez
astfel.

Sparg string-ul ce formeaza descrierea intr-un array de string-uri (cuvintele)
si verific daca acest array contine toate cuvintele din input.

Iau intreaga lista de actori si aplic aceasta metoda pe fiecare in parte. In
caz afirmativ, adaug actorul intr-o noua lista pe care o voi sorta dupa numele
actorilor.

### Videos
Formez lista de filme / seriale (in functie de query) care contine anul si
genul cerut in input. Cu aceasta lista rezolv query-urile:

#### Rating
Formez rating-ul fiecarui video, ca medie a rating-urilor date de o lista de
useri (cea din input), pentru fiecare film, sau fiecare sezon dintr-un serial.
Iar pentru serial fac media rating-urilor pe sezoane. Sortez lista de video
uri dupa rating-url format.

#### Favorite
Pentru fiecare video, calculez de cate ori se afla acesta in lista de video
uri favorite pentru fiecare user dintr-un array de useri.

Sortez lista de video-uri dupa numarul de aparitii in listele de favorite.

#### Longest
Sortez lista dupa durata video-urilor.

#### Most Viewed
Pentru fiecare video, calculez de cate ori apare in lista history (video-uri
vizualizate) pentru fiecare user dintr-o lista de useri.

Sortez lista de video-uri dupa numarul total de vizualizari.

### Users
Pentru fiecare user, map-ul de ratings imi ofera cate rating-uri a dat acel
user. Sortez lista de useri (o copiei a ei) dupa numarul de rating-uri.

## Recommendation
Formez lista de video-uri din filmele si serialele date ca input.
Pentru fiecare recomandare, caut user-ul care are username-ul din input si
aplic pe el recomandarile cerute.

### Basic subscription

#### Standard
Parcurg lista de video-uri si verific daca video-ul curent este deja vizionat
de catre user. Intorc primul video nevazut.

#### Best unseen
Pentru fiecare video, formez rating-ul obtinut ca medie a rating-urilor date
de fiecare user din lista de useri. Apoi sortez aceasta lista de video-uri
dupa rating si caut in lista obtinuta primul video nevazut.

### Premium substription
Verific la fiecare recomandare ca tipul utilizatorului sa fie **premium**.

#### Popular
Creez un map de la un gen la o lista de video-uri care au acel gen; si o lista
de genuri sortate dupa numarul de vizualizari ale userilor (numar cate
vizualizari au toate filmele din genul respectiv pentru o lista de useri)

Parcurg lista de video-uri si iau toate genurile din fiecare video. Pentru
fiecare gen formez numarul de vizualizari pentru filmul curent (care contine
acest gen) si il adaug la numarul total de vizualizari ale genului respectiv.

Adaug si in map-ul care tine genul si lista de video-uri din acel gen, video
ul curent, daca acesta nu a fost deja adaugat.

Sortez map-ul ce numara vizualizarile si pastrez doar numele genurilor
sortate. Parcurg aceasta lista sortata de genuri si gasesc primul film nevazut
de user-ul dat ca input, care este din acel gen (folosind map-ul gen -> lista
de video-uri). 

#### Favorite
Pentru fiecare video, formez numarul de aparitii in listele de favorite video
ale tuturor userilor.

Sortez lista de video-uri dupa acest criteriu, iar in caz de egalitate tin
cont de ordinea de aparitie a acestora in baza de date.

#### Search
Pentru un gen dat ca parametru, gasesc toate video-urile care contin acest
gen. Apoi sortez aceasta noua lista de video-uri dupa rating, criteriul
secundar fiind numele video-ului.

Parcurg lista de video-uri formata si le pastrez doar pe acelea care nu au
fost vizionate de user-ul dat ca input.

