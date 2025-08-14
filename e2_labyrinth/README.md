# Labyrinth — Walker (Rechte-Flosse-Regel)

## Kurzbeschreibung

Implementiere in Java den `Walker`, der nach der **Rechte-Flosse-Regel** ein Labyrinth entlangläuft und überprüft, ob vom Start am Rand das Ziel am Rand erreichbar ist. Zusätzlich muss der komplette Besuchsverlauf an das vorgegebene `Result`-Interface übergeben werden (Logging).

---

## Detaillierte Aufgabenstellung

Du bekommst ein rechteckiges Labyrinth als zweidimensionales `boolean`-Array `maze` (gegeben durch die Hilfsklasse `Maze`).

* `maze[x][y] == true` bedeutet: an Koordinate `(x, y)` befindet sich eine Wand.
* `x` wächst nach rechts, `y` wächst nach unten. `(0,0)` ist oben-left.

Randbedingungen:

* Labyrinth ist mindestens `3 × 3`.
* Alle Felder am Rand sind Wände, **außer**:

  * **Start:** `(1, 0)` (oben, zweite Spalte) — immer frei
  * **Ziel:** `(width-1, height-2)` (rechte Seite, vorletzte Zeile) — immer frei

**Zu implementieren:**
Die Klasse `Walker` mit

1. einem **Konstruktor**, der das Labyrinth (`boolean[][] maze`) und das `Result`-Objekt entgegennimmt und speichert, und
2. der Methode **`boolean walk()`**, die vom Startpunkt `(1, 0)` aus die Rechte-Flosse-Regel anwendet, sich nur orthogonal (N/S/E/W) bewegt und `true` zurückgibt, falls das Ziel erreicht wurde, sonst `false`.

Wichtig:

* `walk()` wird pro `Walker`-Objekt nur **einmal** aufgerufen.
* Das übergebene `maze` darf **nicht verändert** werden.
* **Jedes** Feld, auf dem der Walker **steht**, muss an das übergebene `Result` gemeldet werden (inkl. Start und Ziel).

---

## Anforderungen und Einschränkungen

* **Sprache:** Java (verwende die im Template bereitgestellten Klassen/Interfaces).
* **Logging:** Nutze das im Template vorhandene `Result`-Objekt (Methode `addLocation(...)`) für jeden Feldbesuch.
* **Bewegung:** Nur orthogonale Schritte (keine Diagonalen).
* **Seitenwirkungen:** `maze` darf nicht verändert werden.
* **Performance:** Tests enthalten Labyrinthe, bei denen bis zu 60.000 Felder gelaufen werden. Achte auf effiziente Implementierung (Artemis: \~2s für große Tests).

---

## Eingabeformat (Template-Orientierung)

Das Labyrinth und das `Result`-Objekt werden über den Konstruktor übergeben. Typische Signatur (halboffiziell — nutze die exakten Signaturen im Template):

```java
public class Walker {
    public Walker(boolean[][] maze, Result result) { ... }
    public boolean walk() { ... }
}
```

---

## Ausgabeformat

* Rückgabewert von `walk()`:

  * `true` — Ziel erreichbar (Zielfeld betreten).
  * `false` — Ziel nicht erreichbar.
* Zusätzlich: alle besuchten Koordinaten werden in der Reihenfolge des Betretens über `Result.addLocation(...)` übergeben (inkl. Start und Ziel). Wiederholte Besuche desselben Feldes müssen ebenfalls geloggt werden (eben so oft sie betreten werden).

---

## Beispiele (Verdeutlichung / Logging)

Wenn du z. B. `Maze.generateStandardMaze(10, 10)` mit der Muster-Implementierung nutzt, kann das Logging (Ausgabe des mitgelieferten `StudentResult`) so aussehen:

```
Koordinate (1, 0) hinzugefügt
Koordinate (1, 1) hinzugefügt
Koordinate (1, 2) hinzugefügt
Koordinate (1, 3) hinzugefügt
Koordinate (1, 4) hinzugefügt
Koordinate (1, 5) hinzugefügt
Koordinate (1, 6) hinzugefügt
Koordinate (1, 5) hinzugefügt
Koordinate (2, 5) hinzugefügt
Koordinate (3, 5) hinzugefügt
Koordinate (3, 6) hinzugefügt
Koordinate (4, 6) hinzugefügt
Koordinate (4, 7) hinzugefügt
Koordinate (4, 8) hinzugefügt
Koordinate (3, 8) hinzugefügt
Koordinate (2, 8) hinzugefügt
Koordinate (1, 8) hinzugefügt
Koordinate (2, 8) hinzugefügt
Koordinate (3, 8) hinzugefügt
Koordinate (4, 8) hinzugefügt
Koordinate (4, 7) hinzugefügt
Koordinate (5, 7) hinzugefügt
Koordinate (6, 7) hinzugefügt
Koordinate (6, 8) hinzugefügt
Koordinate (7, 8) hinzugefügt
Koordinate (8, 8) hinzugefügt
Koordinate (9, 8) hinzugefügt
```

> Hinweis: Die konkrete Verwendung von `addLocation(...)` (z. B. welches `Location`-Objekt erwartet wird) folgt der API im Template. Verwende genau die dort vorgegebenen Klassen/Signaturen.

---

## Tests / Akzeptanzkriterien

Deine Implementierung muss folgende Fälle korrekt behandeln:

1. **Lösbares Labyrinth**

   * `walk()` gibt `true` zurück.
   * `Result` listet die komplette Reihenfolge aller besuchten Felder (inkl. Start und Ziel).

2. **Unlösbares Labyrinth**

   * `walk()` gibt `false` zurück.
   * `Result` listet die Reihenfolge der tatsächlich besuchten Felder (Start inkl.).

3. **Keine Modifikation des Labyrinths**

   * Nach `walk()` ist `maze` unverändert.

4. **Logging-Reihenfolge**

   * Felder werden in genau der Reihenfolge geloggt, in der sie betreten wurden. Wiederholte Betretungen werden ebenfalls geloggt.

5. **Performance**

   * Laufzeit und Speicher müssen für Tests mit bis zu 60.000 Schritten innerhalb der angegebenen Limits bleiben.

---

## Randfälle (zum Testen)

* Nicht-quadratische Labyrinthe (`width != height`).
* Kleinste zulässige Größe `3 × 3`.
* Labyrinthe mit vielen Schleifen (mehrfaches Betreten gleicher Felder).
* Vollständig blockierte Fälle (kein Weg zum Ziel).
* Sehr kurze Wege (Start direkt neben Ziel).
* Große Labyrinthe zur Performance-Überprüfung.

---

## Hinweise zur Implementierung (ohne Lösungsspoiler)

* Verwende nur die im Template verfügbaren Helper-Klassen (`Maze`, `Result`, `Location`, etc.). Verändere das `Result`-Interface nicht.
* Logge jedes Feld genau dann, wenn der Walker auf ihm steht (inkl. Start und Ziel).
* Verändere das `maze`-Array nicht.
* `walk()` wird einmal pro Objekt aufgerufen — baue keine Abhängigkeit auf wiederholte Aufrufe.
* Keine nicht-benötigten `System.out.println(...)` in Klassen, die von den Tests verwendet werden (außer in einer eigenen Test-`main`).

---

## Kompilieren / Ausführen (Beispiel)

Angenommen, dein Repo enthält `Main`, `Walker`, `Maze`, `StudentResult`:

```bash
# Kompilieren (Projekt-Root)
javac -d out $(find . -name "*.java")

# Programm starten (ersetze Paket/Name entsprechend)
java -cp out de.tu.uni.Main
```

Oder lokal einfacher:

```bash
javac *.java
java Main
```

(Ersetze `Main`/Paketnamen durch die tatsächlichen Namen im Template.)

---

## Dokumentation & Abgabe

* Lege dieses `README.md` ins Wurzelverzeichnis des jeweiligen Aufgaben-Repos.
* Achte darauf, dass deine Implementierung **keine** zusätzlichen, ungewollten Ausgaben erzeugt und dass du das vom Tester übergebene `Result`-Objekt nutzt (nicht eine selbst erstellte `StudentResult` im Produktivcode).
* Verändere keine vom Template vorgegebenen Signaturen (z. B. des `Result`-Interfaces).

---

## Weiterführendes / Hinweise für Tests

* Nutze die mitgelieferte `main()`-Methode und `StudentResult` zum lokalen Testen; Artemis verwendet eigene `Result`-Subklassen — verwende daher das übergebene `Result`-Objekt.
* Teste verschiedene Größen und Formen (inkl. nicht-quadratischer Labyrinthe), bevor du abgibst.
