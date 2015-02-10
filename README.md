TUWR-Connect
============

TU Wien Racing Team Prüfstandsoftware

Systemvorraussetzungen:
Java Version von 1.5 oder höher

benötigte externe Dateien (r):
.) PCANBasic_JNI.dll
.) PCANBasic.dll

benötigte externe Dateien (r/w):
.) fnct.config
.) command.xml
.) command.dtd
.) racing.db

===============================================================================
Informationen zur "fnct.config" Datei:

Diese Datei wird nur benötigt wenn Funkionen verwenden werden möchten.
Sollte diese Datei nicht existieren nimmt das Programm einfach an das
keine Funktionen benötigt werden und startet sich mit einer leeren
Funktionenliste.

Damit die Funktionen verwendet werden können, muss zu jedem Eintrag in der
"fnct.config" Datei ein passendes Korrespondum in der "command.xml" Datei
angelegt werden.

Die "fnct.config" Datei besitzt die folgenden Einträge:
number_of_functions <- beschreibt wie viele Funktionen existieren sollen
functionX           <- beschreibt die Namen der einzelnen Funktionen
functionX_param     <- beschreibt die Parameter der einzelnen Funktionen

number_of_functions
Dies muss eine positive natürliche Zahl sein, ansonsten wird einfach 0 angenommen

functionX
X ist eine positive natürliche Zahl aus dem Intervall [1,number_of_functions]
und wir nur dann betrachtet, wenn number_of_functions > 0 ist.

functionX_param
X besitzt die gleichen Vorraussetzungen wie in functionX.
Jede Funktion muss diesen zweiten String definieren, sollten jedoch keine 
Parameter verwendet werden, kann dieser String leer gelassen werden.
Mehere Parameter werden mittels einem Strichpunkt (;) getrennt.

Beispiel einer einfachen "fnct.config" Datei:
number_of_functions=2
function1=reset
function1_param=
function2=position
function2_param=value;reset

===============================================================================