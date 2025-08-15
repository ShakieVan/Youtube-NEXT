# AGENTS.md – YouTube WebView Wrapper App (Android)

## Ziel
Eine Android-App, die die **Desktop-Version von YouTube** in einer für **Touch optimierten** WebView darstellt, mit folgenden Funktionen:
- **Pseudo-Fullscreen mit Pinch-to-Zoom**
- **Tabs** ähnlich dem Samsung Browser
- **Automatisches Öffnen von YouTube-Links** in der App
- **Login speichern** (dauerhaft eingeloggt bleiben)
- **Picture-in-Picture (PiP)**, wenn vom Player unterstützt
- **Geringerer Akkuverbrauch** als die offizielle YouTube-App

---

## Architektur
- **Sprache:** Kotlin
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 34+
- **UI-Komponenten:** Android WebView + TabLayout + ViewPager2
- **Speicher:** Persistente Cookies + DOM Storage
- **Vollbild-Handling:** Pseudo-Fullscreen-Layer statt nativer Vollbildmodus
- **Gestensteuerung:** Pinch-to-Zoom, Doppeltipp-Zoom

---

## Hauptmodule
### 1. `MainActivity`
- Initialisiert **WebView** und Tab-Manager
- Implementiert Intent-Filter-Handler (YouTube-Links)
- Steuert die Toolbar (Navigation, Tabs, Einstellungen)

### 2. `CustomWebView`
- Setzt **Desktop User-Agent**
- Aktiviert:
  - `JavaScriptEnabled`
  - `DomStorageEnabled`
  - `setAcceptThirdPartyCookies`
  - `setMixedContentMode(ALWAYS_ALLOW)`
- Behandelt **Pseudo-Fullscreen** und Gesten
- Lädt Desktop-Version von YouTube

### 3. `TabManager`
- Verwalten mehrerer Tabs
- Tabs anlegen, schließen, duplizieren
- Wischen zwischen Tabs
- Speicherung offener Tabs beim Beenden (SharedPreferences/JSON)

### 4. `FullScreenHandler`
- Simuliert Vollbild in einem **Overlay-Container**
- Ermöglicht **Pinch-to-Zoom** im Video
- Optional: Doppeltipp für Zoom-Stufen

### 5. `LinkInterceptor`
- Erkennt `youtube.com` und `youtu.be` Links
- Öffnet diese direkt im aktuellen oder neuen Tab
- Blockiert Standardbrowser, wenn App als Standard gesetzt ist

---

## Funktionen im Detail
1. **Login-Persistenz**
   - Aktivieren von `CookieManager.getInstance().setAcceptCookie(true)`
   - `setAcceptThirdPartyCookies(webView, true)`
   - WebView-Daten nicht bei App-Exit löschen

2. **Tabs**
   - Layout ähnlich Samsung Browser (oben Tab-Leiste)
   - Klick auf „+“ erstellt neuen Tab
   - Langes Drücken auf Tab für Duplizieren/Schließen
   - Tabs in SharedPreferences sichern

3. **Pseudo-Fullscreen**
   - Video wird in einem Overlay-Container vergrößert
   - Pinch-to-Zoom & Doppeltipp-Zoom aktiviert
   - UI-Elemente (Toolbar) ausgeblendet

4. **Picture-in-Picture**
   - Aktiv bei Home-Button oder Back in Fullscreen
   - PiP-Controls: Play/Pause (wenn unterstützt)

5. **Akkusparen**
   - Hardwarebeschleunigung aktiviert
   - Optional: maximale Videoauflösung erzwingen (via JS)

---

## Intent-Filter (AndroidManifest.xml)
```xml
<intent-filter>
    <action android:name="android.intent.action.VIEW"/>
    <category android:name="android.intent.category.DEFAULT"/>
    <category android:name="android.intent.category.BROWSABLE"/>
    <data android:scheme="https" android:host="www.youtube.com"/>
    <data android:scheme="https" android:host="youtube.com"/>
    <data android:scheme="https" android:host="m.youtube.com"/>
    <data android:scheme="https" android:host="youtu.be"/>
</intent-filter>
```

---

## Bekannte Einschränkungen
- **Andere Tonspuren** nur, wenn YouTube-Desktop dies direkt anbietet
- **Echtes Vollbild-Zoom** nicht möglich (nur Pseudo-Fullscreen)
- Änderungen an YouTube-UI können Touch-Optimierungen brechen
- PiP & Hintergrund-Audio hängen vom YouTube-Player ab

---

## ToDo / Erweiterungen
- Qualitätsschnellauswahl (per JS Injection)
- Audio-Only Modus (Video ausblenden, nur Ton streamen)
- Gestensteuerung für „nächstes/vorheriges Video“
- Dark-Theme-Umschalter unabhängig von YouTube-Theme
- Eigenes Startbildschirm-Layout mit Suchfeld + Tabs

---

## Entwicklungsschritte
1. **Projekt in Android Studio anlegen**  
   - Min SDK 26, Target 34  
   - Standard-Empty-Activity  
2. **WebView einrichten**
   - Settings konfigurieren (UA, JS, Cookies, DOM)
3. **Tab-Manager implementieren**
4. **FullScreenHandler** einbauen
5. **Intent-Filter** ins Manifest eintragen
6. **CSS/JS Injection** für Touch-Optimierung
7. **PiP-Mode** aktivieren
8. Testen auf Samsung S24 Ultra

---

## Lizenz / Rechtliches
- Diese App greift auf die **offizielle YouTube-Webseite** zu und verändert lediglich deren Darstellung im WebView.
- Keine Umgehung von DRM oder YouTube-ToS-fremden Zugriffen.
- Nutzung im Rahmen der YouTube-Nutzungsbedingungen sicherstellen.
