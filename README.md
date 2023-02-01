# DatenbankProjekt in Java
### Über das Projekt
Das Projekt wurde in Java mit NetBeans und ScreenBuilder geschrieben.
Fürs Laufen benötigt das Projekt Java JDK 1.8.0_231 und die JAR Datei (mysql-connector-java-5.0.8-bin.jar), die unter Libraries angelegt werden sollte. Außerdem sollte das Programm mit einer SQL Datenbank verbinden lässen, um die Datei in library.sql (unter dieses Verzeichnis) zu nutzen. Dafür habe ich Xampp und localhost/myphpadmin benutzt.
Für das Projekt habe ich Bibliothek II ausgewählt.
### Aufgabenstellung:
> Cocktails

	- Rezepte (Zutaten) für Cocktails in einem Dialog
		erstellen und ändern
	- Cocktails und Rezepte anzeigen und suchen
	- Suche:
		a) nach Cocktailnamen
		b) nach Zutaten 
	
	
> Bibliothek I

	- Bücher befinden sich in Regalen in Fächern
	- Pro Fach können sich mehrere Bücher befinden 
	- Zu einem Buch können mehrer Exemplare 
		existieren
	- Aufgaben :
		-- Zuordnung von Buchexemplaren zu Fächern
		-- Suche von Büchern (nach Titel, Autor, Verlag)
	
> Bibliothek II

	- Zu einem Buch können mehrer Exemplare 
		existieren
	- Pro Buch können mehrere Autoren existieren 
	 Kunden können Bücher leihen
	- Aufgaben :
		-- Suche von Büchern (nach Titel, Autor, Verlag)
		-- Buchentlehnungen mit Abgabefrist
		-- Mahnwesen: falls Abgabefrist überschritten wird
			erfolgt eine kostenpflichtige Mahnung und die
			Abgabefrist berlängert sich um 2 Wochen,
			bis zu 3 Mahnungen pro	Buch möglich,
			danach muss das Buch vom Kunden erstzt werden
		-- Suche nach Entlehnungen (Bücher, Kunden) 
		
		
		
> Campingplatz 

	- Stellplätze haben Namen und sind einer Kategorie zugeordnet 
	- jede Kategorie bietet bestimmte Zusatzleistungen 
		z.Bsp.: direkt am Meer, Stromanschluss, ...
	- Kunden können Stellplätze buchen
	- Aufgaben :
		-- Anzeige der freien/besetzten Stellplätze 
			in einem Zeitraum 
		-- buchen von Stellplätzen 
		-- Anzeige von Buchungen
	

> EKB 

	- Klassen sind in Gruppen unterteilt
	- jede Gruppe hat pro Unterricht einen 
		Lehrer, einen Gegenstand und einen Klassenraum
	- Aufgaben:
		-- interaktive Erstellung eines Stundenplans
			pro Klasse (auch Änderung)
		-- Anzeige des Wochenstundenplans pro Klasse 
