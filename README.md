# Semestrální projekt: Inteligentní továrna

## Abstrakt

Vytvoření aplikace pro virtuální simulaci inteligentní továrny, která simuluje chod výroby – na výrobních linkách za
pomoci strojů a lidí jsou vyráběny produkty. Pracujeme s jednotlivými stroji a hodnotíme jejich využití, spotřebu a
kvalitu výsledných výrobků. Součástí výrobního procesu jsou nejen stroje a lidé, ale také kolaborativní roboti. Základní
časovou jednotkou je jeden takt (trvající jednu hodinu). Stavy továrny se mění (a vyhodnocují) po těchto taktech.

## Funkční požadavky

* **F1.** Hlavní entity, se kterými pracujeme, jsou továrna, linka, stroj, člověk, výrobek a materiál, plus libovolné
  další entity. Stroje, lidé i výrobky mohou být různého druhu.
* **F2.** Produkty se vyrábějí v sériích po několika stech kusech. Pokud se mění série nekompatibilních výrobků, je
  nutné výrobní linky přeorganizovat. Každý výrobek má definovanou sekvenci zařízení, robotů a lidí, které je potřeba
  uspořádat na linku.
* **F3.** Stroje a roboti mají svou spotřebu; lidé, roboti, stroje i materiál generují náklady.
* **F4.** Komunikace mezi stroji, roboty a lidmi probíhá pomocí událostí (eventů). Událost může obdržet 1 až N entit (
  člověk, stroj, robot), které jsou na daný typ události registrované. Události je třeba zpracovat.
* **F5.** Jednotlivá zařízení mají API pro sběr dat. Data zahrnují spotřebu elektřiny, oleje, materiálu a funkčnost (
  opotřebení roste s časem).
* **F6.** Stroje a roboti se po určité době rozbijí. Po rozbití vygenerují událost (alert) s prioritou podle důležitosti
  linky, kterou odbaví člověk – opravář.
* **F7.** Opravářů je omezený počet. Oprava trvá několik taktů. Na začátku a na konci opravy je generována událost.
  Pokud nejsou dostupní žádní opraváři, čeká se, dokud se některý neuvolní. Po uvolnění opravář nastupuje na:
    1. Nejprioritnější defekt.
    2. Nejstarší defekt.
* **F8.** Návštěva ředitele a inspektora. Ředitel prochází továrnou podle hierarchie entit: továrna → linka → (stroj |
  robot | člověk). Inspektor prochází podle míry opotřebení. Ředitel i inspektor mají definované akce, které provádějí s
  daným typem entity. Zaznamenávejte sekvenci procházení a názvy provedených akcí do logu.
* **F9.** Továrna musí umožnit generování následujících reportů za libovolné časové období:
    * **EventReport**: Jaké události vznikly v jednotlivých obdobích, seskupené podle:
        1. Typu události.
        2. Typu zdroje události.
        3. Subjektu, který událost zpracoval.
    * **ConsumptionReport**: Kolik jednotlivá zařízení a roboti spotřebovali elektřiny, oleje a materiálu. Součástí
      reportu je i finanční vyčíslení a souhrny pro nadřazené entity (linka, továrna).
    * **OuttagesReport**: Nejdelší výpadek, nejkratší výpadek, průměrná doba výpadku, průměrná doba čekání na opraváře a
      typy zdrojů výpadků seřazené podle délky výpadku.

## Nefunkční požadavky

* Není požadována autentizace ani autorizace.
* Aplikace může běžet pouze v jedné JVM.
* Aplikaci implementujte tak, aby metody a proměnné, které nemají být dostupné ostatním třídám, byly dobře zapouzdřeny.
  Generovaný Javadoc by měl obsahovat co nejméně veřejných metod a proměnných.

## Analýza

Tato část dokumentace se věnuje analýze softwaru pomocí diagramů UML, které poskytují přehled o architektuře systému.
Diagramy znázorňují strukturu hlavních komponent, jejich vzájemné vztahy a interakce, které umožňují splnění požadavků
na funkčnost.

### Use Cases

Níže je diagram případů použití (Use Case), který znázorňuje hlavní aktéry a jednotlivé případy použití. Diagram
poskytuje přehled o interakcích uživatelských rolí se systémem a definuje hlavní funkcionality dostupné pro každého
aktéra.

![Use case diagram](images/use-cases.png)

### Architektura

Hlavním diagramem projektu je přehledový diagram, který poskytuje vysokou úroveň pohledu na návrh softwaru. Tento
diagram znázorňuje základní komponenty systému a jejich propojení.

![Overview Diagram](images/overview_diagram.png)

První část systému, na kterou se zaměříme, je entita Factory a její související komponenty. Tato entita představuje
abstraktní reprezentaci reálné továrny.

![Factory patterns](images/factory-patterns.png)

Další část systému se zaměřuje na reporty, které lze generovat za libovolné časové období. Reporty poskytují přehled o
výkonnosti a aktivitách systému.

![Report diagram](images/report-diagram.png)

Následující část se věnuje správě událostí (event management). Tento modul umožňuje zpracování událostí v továrně.

![Event system](images/event-system.png)

### Spotřeba

Modul Consumer monitoruje spotřebu zdrojů v továrně. Sleduje využití elektřiny, oleje a materiálů a spravuje procesy
související s jejich spotřebou.

![Consumers](images/consumers.png)

Další část systému je zodpovědná za správu výrobních jednotek a zařízení. Tento modul zabezpečuje efektivní řízení
výroby.

![Production unit and production line](images/production-unit-and-line.png)

V továrně jsou objekty identifikovány pomocí jedinečného identifikátoru (identifier). Tento klíčový prvek zajišťuje
správu objektů.

![Identifier](images/identifier.png)

### Factory Timer

Tiky v továrně jsou řízeny komponentou Factory Timer, která spravuje časové intervaly a akce.

![Factory timer](images/timer.png)

### Opravy

Modul pro opravy zajišťuje správu opravářů a přidělování oprav. Tento modul zajišťuje koordinaci mezi opraváři a
systémem.

![Repair](images/repair.png)
