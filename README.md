# TP Android : Les capteurs

Vous pouvez également voir le tuto sur [cette page](https://narxem.github.io/android-sensors/)

## Introduction

De nombreuses applications présentes sur le store utilisent les capteurs de notre téléphone. Ces capteurs sont toujours de plus en plus nombreux. Dans ce tutoriel, vous allez apprendre comment les exploiter.

Dans ce tutoriel, j'indiquerai _comme ceci_ les fichiers et/ou méthodes dans lesquelles il faut décommenter des instructions

## 1 - Demander la présence d'un capteur

Il peut arriver qu'une application ne peut être utilisée que si le device dispose d'un capteur spécifique. Il n'aurait en effet pas de sens de télécharger un jeu basé sur l'accélèromètre si ce dernier n'est pas présent.

Dans ce cas, on ajoute un tag dans le manifest. Par exemple, pour l'accélèromètre, on ajoutera : 
```xml
<use-feature android:name="android.hardware.sensor.accelerometer" android:required="true"/>
```
_AndroidManifest.xml_

Le paramètre `required` à `true` signifie que le capteur est obligatoire et interdit le téléchargement de l'application s'il n'est pas présent. On peut le mettre à `false` pour indiquer à l'utilisateur que le capteur n'est pas obligatoire mais fortement recommandé.

## 2 - Accès aux capteurs

L'accès aux capteurs se fait via la classe `SensorManager`. Elle permet d'obtenir des informations sur les capteurs présents sur le device.

Pour en obtenir une instance :
```java
SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
```
_MainActivity.java -> onCreate()_

### Utilisation de `SensorManager`

* `List<Sensor> getSensorList(int type)` : obtenir la liste des capteurs du type passé en paramètre (les types seront listés plus loin)
* `Sensor getDefaultSensor(int type)` : obtenir le capteur par défaut du type passé en paramètre


L'entier passé en paramètre est un id de type de capteur. Ces constantes sont définies dans la classe `Sensor`. Les plus utilisées sont :
* `TYPE_ACCELEROMETER` pour l'accélèromètre
* `TYPE_GYROSCOPE` pour le gyroscope
* `TYPE_LIGHT` pour le capteur de lumière
* `TYPE_PROXIMITY` pour le capteur de proximité
* `TYPE_ALL` pour tous les capteurs. Utile dans `getSensorList()` pour obtenir la liste de tous les capteurs

Pour obtenir un capteur :
```java
Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
```
_MainActivity.java -> onStart()_

Pensez à toujours vérifier si le capteur a bien été trouvé, car s'il n'est pas présent, alors cette méthode vous renverra `null` et fera planter votre application par une jolie `NullPointerException` si vous tentez d'utiliser la variable directement ! Prévoyez toujours un comportement à adopter si aucun capteur n'est présent.

_MainActivity.java -> onStart()_

## 3 - Exploitation du capteur

Nous allons maintenant faire appel à une nouvelle interface : `SensorEventListener` qui va écouter nos capteurs pour transmettre les valeurs.


Il faudra alors créer une classe qui implémente cette interface, et qui contiendra 2 méthodes :

### `void onAccuracyChanged(Sensor sensor, int accuracy)`
Cette méthode est appelée en cas de changement de précision du capteur. Dans notre cas, on peut tout simplement ne rien faire.


### `void onSensorChanged(SensorEvent event)`
Cette méthode nous interresse plus, elle est appelée dès que le capteur detecte un changement. L'objet `SensorEvent` contient des données utiles : 
* `event.sensor` : le capteur en question
* `event.accuracy` : la précision du capteur
* `event.timestamp` : la nanoseconde à laquelle le changement a été détecté
* `event.values` : un tableau de `float` contenant les valeurs qui nous interressent

Il faut donc faire notre traitement dans cette méthode. 
/!\ : Il ne faut JAMAIS lancer d'opération bloquante ou d'opération gourmande en mémoire ici, car cette méthode sera invoquée très souvent. Vous pourriez saturer la mémoire du device très rapidement.

_MainActivity.java -> Classe interne `AccelerometerListener`_
_MainActivity.jave -> ligne 30 (`SensorEventListener`)_

## 4 - Ecoute du capteur

Dernière étape : nous avons notre listener, il faut maintenant écouter notre capteur.
On fait cela de nouveau à l'aide de notre objet `SensorManager`.
On utilise la méthode `registerListener(SensorEventListener listener, Sensor sensor, int delay)` avec
* `listener` : une instance du `SensorEventListener` que nous venons de créer
* `sensor` : le capteur à écouter
* `delay` : le délai entre chaque écoute. Cela peut être :
    - `sensorManager.SENSOR_DELAY_NORMAL` : pour une écoute "normale", c'est le délai le plus lent
    - `sensorManager.SENSOR_DELAY_UI` : plus rapide, pour une écoute qui met à jour l'interface
    - `sensorManager.SENSOR_DELAY_GAME` : encore plus rapide, pour une utilisation en jeu
    - `sensorManager.SENSOR_DELAY_FASTEST` : le plus rapide, rarement utile.

Enfin, il ne faut pas oublier de désactiver le listener avec la méthode `unregisterListener(SensorEventListener listener)` lorsqu'on ne l'utilise pas. Je conseille de le faire dans le `onPause()` ou le `onStop()` (et donc `registerListener` respectivement dans `onResume()` ou `onStart()`).

_MainActivity.java -> onStop()_


# TP

Vous pouvez maintenant tenter d'exploiter ce tutoriel pour utiliser le capteur d'orientation de votre téléphone

L'activité (improprement) appelée `GyroscopeActivity` affiche une image et fournie une méthode permettant de faire subir une rotation à cette image. 

A vous de faire en sorte de le creeper reste toujours dans le même sens quand vous pivotez votre téléphone / tablette (l'activité est verouillée en mode portrait)

Vous disposez de plusieurs possibilités pour la créaction de votre listener : 
* Vous pouvez faire un classe interne, comme dans la `MainActivity.java`
* Vous pouvez en faire un classe anonyme au moment de l'enregistrement du capteur et du listener
* Vous pouvez faire que l'activité implémente `SensorEventListener` et définir directement les méthode dans l'activité. A ce moment la, mettez `this` comme listener au moment de l'enregistrement

Enfin, `GyroscopeActivity` contient la méthode `rotateCreeper(float angle)` pour faire tourner l'image d'un certain angle (en degrés) par rapport à sa position de départ

N'hésitez pas à vous inspirer de `MainActivity` si vous avez des difficultés.


### Solution
Une solution se trouve sur la branche final-code. Pour y acceder :
```bash
git checkout final-code
```
