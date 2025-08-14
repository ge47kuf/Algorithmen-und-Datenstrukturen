# Verbessertes Sortieren — Aufgabe: Kombination & Optimierung von Sortieralgorithmen

## Kurzbeschreibung

Erweitere und optimiere die bereitgestellten Sortieralgorithmen in **Java**, ohne das vorgegebene Logging-Verhalten zu verändern. Ziel ist, mehrere Pivot-Strategien für Quicksort bereitzustellen, Mergesort effizienter zu machen (gemeinsames Hilfsarray + frühzeitiges Abbrechen, falls bereits sortiert), Selectionsort als Fallback für kleine Teilbereiche einzusetzen und eine Dual-Pivot-Quicksort-Variante zu implementieren. Die mitgelieferte `SortingComparison`-Klasse dient zum Messen und Vergleichen der Laufzeiten.

> Datei: Lege dieses Dokument als `README.md` in dein Übungs-Repository.

---

## Überblick / Ziele

* **Quicksort**: Pivot-Auswahl über ein `PivotFinder`-Interface (mehrere Strategien). Gewählter Pivot muss vor dem Partitionierschritt an das Ende des aktuellen Teilbereichs getauscht werden.
* **DualPivotQuicksort**: Implementiere eine Dual-Pivot-Version (zwei Pivots, Aufteilung in drei Teilbereiche). Unterstützt ebenfalls verschiedene Pivot-Strategien über ein `DualPivotFinder`-Interface.
* **Mergesort**: Verwende ein einmal erzeugtes Hilfsarray für alle Merge-Operationen. Beende das Sortieren eines Teilbereiches frühzeitig, wenn dieser bereits sortiert ist (Überprüfung nach dem Loggen des Starts).
* **Selectionsort**: Verwende Selectionsort als Fallback, wenn die zu sortierende Teilbereichslänge `<= threshold` ist (Threshold wird im Konstruktor/Setup übergeben). Diese Optimierung ist für Mergesort, Quicksort, DualPivotQuicksort und MergesortSimple anzuwenden.
* **Pivot-Strategien**: Implementiere mehrere Pivotfinder-Strategien (siehe Abschnitt „PivotFinder“).
* **Logging**: Logging-Verhalten aus Template übernehmen — Änderungen, die das Logging verändern, sind **nicht** erlaubt.

---

## Erwartete / zu verändernde Klassen (Template-orientiert)

* `PivotFinder` (Interface / Klasse mit statischen Factory-Methoden)
* `Quicksort` (nutzt `PivotFinder`)
* `DualPivotFinder` (Interface / Klasse mit statischen Factory-Methoden)
* `DualPivotQuicksort`
* `Mergesort` (optimiert gegenüber `MergesortSimple`)
* `MergesortSimple` (bestehende, einfache Implementierung)
* `Selectionsort` (bestehende Implementierung — wird als Fallback verwendet)
* `SortingComparison` (Main zum Vergleichen; bereits vorhanden)
* ggf. zusätzliche Hilfsklassen (nur wenn Logging unverändert bleibt)

> Nutze **genaue Signaturen aus dem Template**. Verändere keine Methoden/Schnittstellen, wenn Tests davon abhängen.

---

## PivotFinder (Quicksort) — Anforderungen

Ein `PivotFinder` bietet die Methode:

```java
int findPivot(int[] numbers, int from, int to)
```

* `from` und `to` sind inklusiv.
* Rückgabe: Index des gewählten Pivots (im Bereich `[from..to]`).

Implementiere folgende statische Factory-Methoden / Strategien:

* **LastPivot** — bereits vorhanden: wählt das letzte Element (`to`).
* **MidPivot** — wählt die mittlere Position im Bereich. Bei gerader Länge: linke Mitte (abrunden).
* **RandomPivot(seed)** — wählt zufällig einen Index im Bereich (`java.util.Random` mit gegebenem `seed`, `nextInt(bound)` sinnvoll verwenden).
* **MedianPivotFront(numberOfConsideredElements)** — betrachte die ersten `x` Elemente des Suchbereichs (nicht außerhalb von `to`), bestimme ihren Median nach Größenordnung und gib dessen Index zurück. Bei gerader Anzahl: kleinerer Mittelwert (linker Mittelwert) ist akzeptabel.

  * Beispiele:

    * Array `[0..9]`, `from=0,to=9`, `x=3` → betrachte indices `0,1,2` → Median = index `1`.
    * Bei `from` ≠ 0 oder wenn `x` > Länge des Bereichs, betrachte nur bis `to`.
* **MedianPivotDistributed(numberOfConsideredElements)** — betrachte `x` Elemente gleichverteilt über den Bereich (erstes Element ist `from`, Abstand so groß wie möglich und gleichmäßig), bestimme den Median der betrachteten Werte und gib Index dieses Median-Wertes zurück.

  * Beispiele:

    * `[0..9]`, `from=0,to=9`, `x=3` → indices `0,4,8` → Median → index `4`.
    * Abstand wähle maximal möglich so dass der letzte Index `<= to`.

> Die Median-Implementierungen werden nur für `x = 3` und `x = 5` getestet, müssen aber sinnvolle Ergebnisse für alle Bereiche `length >= 2` liefern.

---

## DualPivotFinder (DualPivotQuicksort) — Anforderungen

Ein DualPivotFinder liefert zwei Pivot-Indices:

```java
int[] findPivots(int[] numbers, int from, int to) // returns array of length 2 (two indices)
```

* Die Reihenfolge der zurückgegebenen Indices ist nicht festgelegt (die Implementierung muss klären, welcher Pivot der kleinere bzw. größere ist).
* Zu implementierende Strategien (statische Factory-Methoden):

  * **FirstLastPivot** — liefert `[from, to]` (kleinster und größter Index).
  * **RandomPivot(seed)** — liefert zwei unterschiedliche zufällige Indices im Bereich (zweiter Pivot so lange zufällig neu wählen bis ≠ erster).
  * **MedianPivotFront(x)** — betrachte die ersten `x` Elemente im Bereich, wähle aus ihnen den zweit-kleinsten und zweit-größten Wert (für `x=5` entspricht das zweitkleinster/zweitgrößter) und gib deren Indices zurück; zuerst zurückgegebener Wert soll Index des kleineren Pivots sein.
  * **MedianPivotDistributed(x)** — analog zu `MedianPivotFront`, aber die `x` Elemente gleichverteilt im Bereich wählen.
* Die Median-basierten Dual-Pivot-Strategien werden nur mit `x = 5` getestet (also zweitkleinster / zweitgrößter), müssen aber sinnvoll für Bereichslängen ≥ 2 funktionieren.

---

## Mergesort — Verbesserungen

* **Ein einziges Hilfsarray**: Erstelle ein Hilfsarray einmalig vor dem Rekursionsstart und verwende es für alle Merge-Operationen. In jeder Merge-Methode ist nur garantiert, dass das Hilfsarray mindestens so groß ist wie der aktuell zu verarbeitende Teilbereich.
* **Frühzeitiges Abbrechen**: Nach dem Loggen des Starts eines Sortierschrittes soll überprüft werden, ob der Teilbereich bereits sortiert ist; falls ja, den Sortierschritt abbrechen (keine Rekursion / kein Merge).
* **Selectionsort-Fallback**: Nach dem Start-Log prüfen, ob Bereichslänge ≤ `selectionThreshold` → falls ja, Selectionsort aufrufen (Selectionsort loggt selbst seinen Start).

> Logging-Verhalten aus Template beibehalten (Start-Log, ggf. Teilbereich-Logs vor Rekursionen).

---

## Selectionsort — Verwendung als Fallback

* **Ziel:** Für kleine Teilbereiche (Länge ≤ `selectionThreshold`) ist Selectionsort oft schneller; daher in allen Sortieralgorithmen (Mergesort, Quicksort, DualPivotQuicksort und MergesortSimple) nach dem Start-Log prüfen, ob Bereichslänge ≤ `selectionThreshold` und dann Selectionsort verwenden.
* `selectionThreshold` wird im Konstruktor bzw. Konfigurations-Parameter übergeben (siehe Template).

---

## Dual-Pivot-Quicksort — Anforderungen

* Implementiere Dual-Pivot-Partitionierung nach den in der Vorlesung beschriebenen Regeln (Aufteilung in drei Bereiche: `< pivotSmall`, `between`, `> pivotLarge`).
* Tausche die beiden gewählten Pivots (gemäß `DualPivotFinder`) an die gewünschten Endpositionen bevor Partitionierung beginnt; sorge dafür, dass die Implementierung stets die tatsächlichen Pivots verwendet (nicht durch Tausch verwechselt).
* Logging:

  * Logge den Start des DualPivotQuicksort-Schrittes (Indices des Teilbereichs).
  * Logge vor den rekursiven Aufrufen die drei Teilbereiche (wie im Template-Beispiel).
* **Selectionsort-Fallback** wie oben (prüfen nach Start-Log).
* Stelle sicher, dass auch für sehr kleine Teilbereiche sinnvolle Ergebnisse geliefert werden (z. B. beim `to < from` oder leeren Teilbereichen).

---

## Logging: WICHTIG

* **Das vorhandene Logging-Verhalten darf nicht verändert werden.** Das Template loggt bereits bestimmte Ereignisse (Start eines Sortierschrittes, ggf. Merge-Starts, Selectionsort-Start, etc.). Deine Änderungen dürfen diese Logs nicht erweitern, entfernen oder in ihrer Reihenfolge ändern.
* Wenn du zusätzliche Hilfsmethoden oder Klassen anlegst, dürfen diese **kein** Logging durchführen, das von Tests ausgewertet wird.
* Tests vergleichen die Laufzeit zwischen der unoptimierten und optimierten Variante **bei identischem Logging**.

---

## Beispiele (aus der Aufgabenbeschreibung)

**Quicksort – Pivotwahl Beispiel:**

* Eingabe: `[0,1,2,3,4,5,6,7,8,9]`, `from=0,to=9`, `MidPivot` → Index `4` (bei gerader Länge linke Mitte wäre `4` für 10 Elemente? → beachte: bei 10 Elementen linke Mitte = `4`).
* `MedianPivotFront(x=3)` auf `[9,1,8,5,2,3,5,1,0,7]`, `from=0,to=9` → betrachte `9,1,8` → Median `8`? (nach Größenordnung median ist 8? — Implementierung wählt mittleren nach Sortierung der betrachteten Werte; Beispiel in Template: median der ersten 5 => 5 ergibt Index 3).

**DualPivotQuicksort – Beispiel (aus Template):**

* Array: `[24, 8, 42, 10, 75, 29, 77, 38, 57]`
* Pivotwahl `FirstLastPivot` → Pivots `24` (`from`) und `57` (`to`).
* Nach Partitionierung und Rekursionen sollen die drei Teilbereiche geloggt werden und das Endergebnis vollständig sortiert sein.

> (Die konkreten Zahlen-/Index-Beispiele sind im Template ausführlicher beschrieben — übernimm diese, falls dein README eine Referenz benötigt.)

---

## Tests / Akzeptanzkriterien

* **Korrektheit:** Alle Algorithmen müssen das Array korrekt sortieren (aufsteigend).
* **Logging-Konformität:** Logging unverändert; Tests vergleichen Laufzeit zwischen unoptimierter und optimierter Variante bei gleichem Logging.
* **PivotFinder:** Alle PivotFinder/Strategien müssen gültige Indices innerhalb des Bereichs zurückgeben; Median-Finder müssen bei testspezifischen Parametern (3 und 5) die erwarteten Ergebnisse liefern.
* **Mergesort-Optimierungen:** Verwendung eines einmaligen Hilfsarrays; frühzeitiges Abbrechen bei bereits sortetem Teilbereich.
* **Selectionsort-Fallback:** Wird korrekt aufgerufen, wenn Teilbereichslänge ≤ `selectionThreshold`.
* **DualPivotQuicksort:** Partitionierung in drei Bereiche korrekt, Pivots werden benutzt wie zurückgegeben, Logging wie vorgegeben.
* **Performance:** Gemessene Verbesserungen gegenüber unoptimierter Version sollen erkennbar sein; Infrastruktur führt Vergleiche mit Arrays in der Größenordnung von Millionen Elementen (siehe Performance).

---

## Performance-Anforderungen

* Tests verwenden Arrays der Länge **2,5 bis 7,5 Millionen**.
* Für die Messergebnisse läuft unoptimierte + optimierte Variante hintereinander → Timeout für Vergleichs-Run beträgt **\~4 Sekunden** (daher je Variante \~2s praktikabel).
* Implementierungen müssen möglichst effizient sein (O(n log n) im Mittel für Quicksort/Mergesort; DualPivotQuicksort sollte vergleichbar effizient sein). Vermeide unnötige Allokationen und Kopien, die die Laufzeit deutlich erhöhen.

---

## Hinweise & Beschränkungen

* **Keine zusätzlichen Logs** in neuen Klassen/Methoden, die die Tests verfälschen könnten.
* Du darfst neue Klassen erstellen (z. B. Hilfsklassen für Pivot-Strategien), solange sie das Logging nicht beeinflussen.
* Änderungen, die Logging-Signale entfernen, um Laufzeit zu gewinnen, sind **nicht erlaubt**.
* Verändere keine public API/Signaturen, auf die `SortingComparison` oder Tests zugreifen.
* Median-Implementierungen: bei gerader Anzahl der betrachteten Werte ist der kleinere mittlere Wert (linker Median) zulässig.

---

## Kompilieren / Ausführen (Beispiel)

Verwende das mitgelieferte Projekt-Template. Typische Kommandos (Projekt-Root):

```bash
# Kompilieren (angepasst an Projektstruktur)
javac -d out $(find . -name "*.java")

# Beispiel: SortingComparison starten (ersetze Paketnamen entsprechend)
java -cp out de.tu.uni.SortingComparison
```

`SortingComparison` enthält Konfigurationsoptionen (Kommentare/flags) zum Ein- und Ausschalten verschiedener Messläufe und Pivot-Strategien.

---

## Testempfehlungen (lokal)

* Vergleiche unoptimierte vs. optimierte Versionen mit `SortingComparison` für unterschiedliche Datenszenarien:

  * komplett zufällige Arrays,
  * Arrays mit vielen gleichen Werten,
  * bereits (teilweise) sortierte Arrays,
  * umgekehrte Arrays,
  * Arrays mit kleinen „unsortierten“ Blöcken.
* Teste alle Pivot-Strategien (Mid, Random, MedianFront, MedianDistributed) für Quicksort; testweise auch Dual-Pivot-Strategien mit `x=5`.
* Miss Laufzeiten jeweils für große Arrays (mehrere Millionen) — `SortingComparison` ist dafür vorgesehen.

---

## Dokumentation & Abgabe

* Lege dieses `README.md` ins Root-Verzeichnis des Aufgaben-Repos.
* Implementiere die geforderten Änderungen in den vorgegebenen Klassen.
* Stelle sicher, dass Logging und Signaturen unverändert bleiben.
* Nutze `SortingComparison` zur Validierung und Performancemessung; aber die Korrektheit (Sortierung) ist Pflicht.
