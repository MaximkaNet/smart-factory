# Patterny:

1. Iterator
   na iteraciu cez factory pouzivame dva iteratory(factory/iterator/UsageIterator a factory/iterator/TreeIterator)

2. Observer  
   a) upozornenie entit na zaciatok noveho taktu(factory/TickObserver)  
   b) upozornenie na vyvolanie urciteho druhu eventu (event/EventListener)

3. Builder  
   a) na komplikovane postavenie factory pouzivame (factory/FactoryBuilder)  
   b) na vytvorenie Timeru sa bouziva (timer/TimerBuilder)

4. Facade  
   (event/EventBus) oddeluje event management od zbytku aplikacie

5. Abstract Factory  
   (consumer/AbstractConsumer) je genericky a slouží k ukládání informací o spotřebě (materiálu, elektřiny a oleje).

6. Chain of Responsibility  
   (productionunit/AbstractProductionUnit) ma odkaz na dalsi unit, vyroba produktov prebieha tak ze jednotlive produkty
   sa v chaine posuvaju dalej

7. Visitor  
   Objekt (visitor/Visitor) vie navstivit rozne druhy tovarenskych unitov(roboty, pracovnikov, masiny)

8. State  
   a) (productionunit/AbstractProductionUnitState) zastresuje stavy produkcnich jednotiek
   b) Mame konkretni implementace stavu (state) pro konkretni entity (worker/Worker, equipment/Robot, equipment/Machine)

9. Object pool  
   a) Produkcne linky su manageovane v object poole (productionline/ProductionLinePool)  
   b) Opravari su manageovany v objekt poole (repair/RepairmanPool)

10. Factory
    Identifikatory pre produkcne jednotky su vytvorene pomocou (identifier/IdentifierFactory)