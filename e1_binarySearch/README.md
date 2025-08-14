# Binäre Suche — Pinguinsupermarkt

## Kurzbeschreibung
Implementiere in Java mehrere Varianten der **binären Suche** zur Bestimmung von Indexen oder Indexbereichen in sortierten Arrays. Zusätzlich muss jede geprüfte Mitte (Schritt) an ein gegebenes `Result`-Interface geloggt werden. Die Aufgabe ist in drei aufbauende Teile unterteilt (ein Wert, eine Grenze, ein Intervall).

---

## Detaillierte Aufgabenstellung

Du arbeitest mit aufsteigend sortierten Integer-Arrays (`int[] sortedData`) — das Array enthält mindestens einen Wert. Die im Template bereits vorhandenen Klassen/Interfaces `Result`, `Interval` und `NonEmptyInterval` werden genutzt.

Implementiere die folgenden Methoden in der Klasse `BinSea` (Signaturen orientativ — benutze die exakten Signaturen aus deinem Template):

1. `int search(int[] sortedData, int value, Result result)`  
   - Führe eine klassische binäre Suche durch. Bei jedem Blick auf das mittlere Element des aktuellen Suchbereichs übergib dessen Index an `result` (Logging).  
   - Sobald das mittlere Element gleich `value` ist, gib dessen Index zurück.  
   - Wenn der Suchbereich auf Größe ≤ 1 reduziert ist und `value` nicht gefunden wurde, brich ab. Gib in diesem Fall einen Index zurück, der entweder auf das nächstgrößere oder das nächstkleinere Element zeigt — welcher von beiden gewählt wird, ist egal, **solange** der Index innerhalb des Arrays liegt.  
   - Beim Finden/loggen einer Mitte in einem Bereich mit gerader Anzahl an Elementen ist es egal, welcher der beiden möglichen mittleren Indices gewählt wird.

2. `int search(int[] sortedData, int value, boolean lowerBound, Result result)`  
   - Baue auf Methode (1) auf: führe dieselbe binäre Suche durch und logge die mittleren Indices wie dort. (Keine zusätzlichen Log-Einträge durch die Nachbearbeitung.)  
   - Nachdem die Suche beendet ist, liefere **bei `lowerBound == true`** den kleinstmöglichen Index zurück, an dem ein Arrayeintrag ≥ `value` steht (untere Schranke).  
   - Bei **`lowerBound == false`** liefere den größtmöglichen Index zurück, an dem ein Arrayeintrag ≤ `value` steht (obere Schranke).  
   - Falls durch diese zusätzliche Einschränkung kein gültiger Index gefunden werden kann (z. B. gesuchte obere Grenze liegt links außerhalb des Arrays), gib `-1` zurück.  
   - Log-Einträge dürfen nur die in der eigentlichen binären Suche geprüften Mittelpunkte enthalten — zusätzliche Schritte zur Anpassung der Grenze dürfen **nicht** geloggt werden.

3. `Interval search(int[] sortedData, NonEmptyInterval valueRange, Result resultLower, Result resultHigher)`  
   - Suche die Indices im Array, die alle Elemente enthalten, welche in das geschlossene Werteintervall `valueRange` fallen.  
   - Verwende dabei für die Suche der Intervallgrenzen die bereits implementierten Suchmethoden (oder ein analoges Verfahren) und logge die in diesen Teil-Suchen auftretenden Prüf-Mittelpunkte jeweils an `resultLower` (für die untere Grenze) und `resultHigher` (für die obere Grenze).  
   - Falls die Suche nach der unteren Grenze bereits zeigt, dass kein Arraywert in `valueRange` liegen kann, darf die Suche nach der oberen Grenze **nicht** gestartet werden und es darf **nichts** in `resultHigher` geloggt werden.  
   - Liefere als Ergebnis ein `Interval`, das die gefundenen Indices (inklusive) angibt. Falls kein Wert im Array im Bereich liegt, gib ein **leeres** `Interval` zurück.

> **Wichtig:** Fehlerbehandlung für unsortierte Arrays ist nicht erforderlich. Wohl aber müssen Fälle behandelt werden wie: gesuchte Werte außerhalb der Array-Grenzen, mehrfach vorkommende Werte, nicht-quadratische Arrays usw.

---

## Anforderungen & Einschränkungen
- **Sprache:** Java (nutze die Klassen/Signaturen aus dem Aufgaben-Template).  
- **Logging:** Jeder Zugriff auf die mittlere Position während der binären Suche muss durch Übergabe des geprüften Indexes an das übergebene `Result`-Objekt geloggt werden (z. B. `result.addStep(index)` — benutze die im Template vorgegebene Methode). Keine zusätzlichen Logeinträge außer diesen Mittelschritten.  
- **Rückgabewerte:** Halte dich strikt an die oben beschriebenen Rückgaberegeln (`int`-Index, `-1` wenn nicht möglich, `Interval` oder leeres `Interval`).  
- **Seitenwirkungen:** Verändere `sortedData` nicht.  
- **Korrektheit:** Mehrfach vorkommende Werte müssen korrekt gehandhabt werden (bei Grenzsuche die jeweils kleinste/größte passende Position).  
- **Performance:** Achte auf die angegebenen Testlimits (siehe Abschnitt „Performance“).

---

## Erwartete Signaturen (Beispiel, nutze exakte Template-Signaturen)
```java
public class BinSea {
    public int search(int[] sortedData, int value, Result result) { ... }

    public int search(int[] sortedData, int value, boolean lowerBound, Result result) { ... }

    public Interval search(int[] sortedData, NonEmptyInterval valueRange,
                           Result resultLower, Result resultHigher) { ... }
}
````

---

## Logging (genau)

* Bei jeder betrachteten Mitte des aktuellen Suchbereichs muss **einmal** der entsprechende Index an das jeweilige `Result`-Objekt gemeldet werden.
* Bei der Suche nach einem Intervall werden zwei separate `Result`-Objekte übergeben — `resultLower` für die Suche der unteren Intervallgrenze und `resultHigher` für die obere. Log-Einträge der unteren Grenze dürfen nicht in `resultHigher` erscheinen und umgekehrt.
* Zusätzliche „Nachschritte“ zur Anpassung eines gefundenen Indexes (z. B. ein manuelles Nach-links/-rechts-Schieben, um die exakte Grenze zu erhalten) dürfen **nicht** geloggt werden.

---

## Beispiele (Veranschaulichung & erwartetes Logging)

**Array:** `[2, 7, 7, 42, 69, 1337, 2000, 9001]`

### 1) `search(sortedData, 7, result)`

* Mittelpunkte geprüft (Beispielausgabe): Index 3 (`42`), Index 1 (`7`)
* Rückgabe: `1` (oder `2` wäre bei Mehrfachfund akzeptabel, solange ein Vorkommen zurückgegeben wird)
* Beispiel-Logging (in Reihenfolge):

  ```
  added step to index 3
  added step to index 1
  ```

### 2) `search(sortedData, 100, result)`

* Mittelpunkte geprüft: Index 3 (`42`), Index 5 (`1337`), Suchbereich dann zu Größe 1 → Abbruch
* Rückgabe: Ein Index nahe `100` (z. B. `4` oder `5`; Index muss im Array liegen)
* Logging:

  ```
  added step to index 3
  added step to index 5
  ```

### 3) `search(sortedData, 7, /* upperBound */ false, result)`

* Binäre Suche wie oben, gemeldete Mittelpunkte: z. B. Index 3, Index 1
* Da nach der **Obergrenze** gesucht wird und gleiche Werte mehrfach vorkommen, muss die größte Position zurückgegeben werden (z. B. `2`).
* Logging bleibt:

  ```
  added step to index 3
  added step to index 1
  ```

### 4) Intervall-Suche `search(sortedData, [7,1500], resultLower, resultHigher)`

* Erwartetes Ergebnis: `Interval [1,5]` (Indices der ersten/letzten Elemente im Intervall).
* Logging (Mittelpunkte in Reihenfolge der beiden Suchen):

  ```
  added step to index 3    (untere Grenze)
  added step to index 1    (untere Grenze)
  added step to index 3    (obere Grenze)
  added step to index 5    (obere Grenze)
  added step to index 6    (obere Grenze)  <-- Beispiel, wenn noch geprüft wurde
  ```
* Wenn die Suche nach der unteren Grenze bereits ergibt, dass kein Element im Intervall liegen kann, darf `resultHigher` **gar nicht** geloggt werden.

---

## Tests / Akzeptanzkriterien

* **Richtigkeit**: Für viele unterschiedliche Testfälle (einschließlich mehrfach vorkommender Werte, Werte außerhalb des Arrays) müssen die Rückgabewerte korrekt sein.
* **Logging**: Für jede Suche müssen genau die geprüften Mittelpunkte geloggt werden — in der richtigen Reihenfolge. Keine zusätzlichen Logs.
* **Intervall-Suche**: Falls die untere Grenze keine Treffer zulässt, ist das Ergebnis ein leeres `Interval` und `resultHigher` bleibt unverändert.
* **Fehlerfälle**: Rückgabe `-1` nur wenn die Grenzbedingung keinen gültigen Index ermöglicht (gemäß Beschreibung).
* **Seitenwirkungen**: `sortedData` bleibt unverändert.

---

## Randfälle (zu prüfen)

* Gesuchte Werte kleiner als das kleinste Array-Element.
* Gesuchte Werte größer als das größte Array-Element.
* Array mit vielfachen identischen Werten.
* Arrays sehr kleiner Länge (z. B. `length == 1`, `length == 2`).
* Nicht-quadratische Arrays (irrelevant hier, da 1D).
* Intervall, das vollständig links/rechts außerhalb des Arrays liegt.
* Intervall, das genau ein Element umfasst.

---

## Performance

* Teile 1 & 2 werden mit Arrays der Größe **3.000.000** getestet; es werden **3.000.000** Suchanfragen in ca. **1 Sekunde** erwartet.
* Teil 3 wird mit Arrays der Größe **1.250.000** geprüft; es werden **1.250.000** Intervallsuchen in ca. **2 Sekunden** erwartet.
* Implementierungen müssen daher O(log n) pro Suche erreichen und keine unnötigen Allokationen oder linearen Operationen innerhalb der Suche durchführen.

---

## Kompilieren / Ausführen (Hinweis)

* Benutze die mitgelieferten Template-Dateien, Kompilieren/Run-Beispiele siehe Projekt-Template. Achte darauf, dass Tests/Artemis keine zusätzlichen Ausgaben erwarten — vermeide `System.out` in den zu bewertenden Methoden.

---

## Dokumentation & Abgabe

* Lege diese Datei (`README.md`) ins Root-Verzeichnis des Aufgaben-Repos.
* Die Implementierung darf die vorgegebenen Signaturen und das `Result`-Interface nicht verändern.
* Verwende zum lokalen Testen die mitgelieferten `StudentResult`-Hilfsklassen, aber achte darauf, bei Abgaben das beim Test übergebene `Result`-Objekt zu verwenden.

---

## Autor / Quelle

Aufgabenstellung: Übung aus dem Modul **Datenstrukturen** (Thema: Binäre Suche — Pinguinsupermarkt). Beschreibung und Beispiele stammen aus dem offiziellen Aufgaben-Template der Lehrveranstaltung.

```
```
