Turkish: merhabalar eğer türkseniz ve ingilizce bilmiyorsanız oyun motoru hakkında size yardımcı olmaktan memnuniyet duyarım yardıma ihtiyacınız olursa mailime yazabilirsiniz.(mail altta yazıyor.)

if you make a game with tengine you dont need to pay me or something, just show me your game. mail: eneskobal18@gmail.com

# TEngine
This is a 2D game engine that written on top of LWJGL 3. [Thunder Engine]
# Very Short Description
This game engine is made for 2D games. 
If you want you can make a sweet bunny running around in the meadow, or
A samurai fighting enemies on the platform. It's up to you.
(I gave these examples on purpose. You can use TGameExamples to look.)
# System
Engine system works nearly staticly, for example this is a code block that runs a blank screen;
```java
public static void main(String[] args) {

        TEngine.attachTPartToEnvironment(new ITPart() {
            @Override
            public void init() {
                TEngine.ENGINE_SCENE$SYSTEM.changeScene(new TScene(TEngine.ENGINE_CAMERA) {
                    @Override
                    public void update(float dt) {

                    }
                });
            }

            @Override
            public void loop(float dt) {

            }

            @Override
            public void dispose() {

            }
        }, TExampleGames.class);
        TEngine.ENGINE.start();
    }
```
![image](https://github.com/TDevvv/TEngine/assets/46716625/1ad5213c-f649-4eeb-95ec-5934fda5daa4)

if you create a class for running engine, you can make class to implement ITMHasFirst and change windows addittional 
configs. for example ;
```java
@Override
    public void first() {
        TEngine.ENGINE_WINDOW.setWindowSize_i(new TMScale(500,500)); //change window size
        TEngine.ENGINE_WINDOW.TWindow_background.changeColor(0,1,0); //change background color to green. 
        TEngine.ENGINE_WINDOW.addGLFWInitWindowHint(new Vector2i(GLFW.GLFW_MAXIMIZED,GLFW.GLFW_TRUE)); //add window hint to GLFW.

        //or you can create a new Window config.
        TEngine.ENGINE_WINDOW.TWindow_config = new TWConfig(
                500,500,"TExample"
        );
        TEngine.ENGINE_WINDOW.TWindow_background.changeColor(0,g,0);
        TEngine.ENGINE_WINDOW.addGLFWInitWindowHint(new Vector2i(GLFW.GLFW_MAXIMIZED,GLFW.GLFW_TRUE));

    }
```

# Features
| Feature  | State |
| ------------- | ------------- |
| Input (Keyboard\Mouse [Joystick Planned])  | Have |
| Window Handling (GLFW)  | Have |
| Physics (Box2D)  | Have |
| 2D Rendering   | Have  |
| Scene System   | Have  |
| Mapping (T2DMap)   | Have  |
| Sprite Sheet   | Have  |
| Font Renderer   | Planned  |
| Animation   | Have  |
| Debug Drawing   | Have  |
| I\O   | Have  |
| Online-Multiplayer   | Have  |
| Component System   | Have  |
| Logger   | Have  |
| File Handling   | Have  |
# Demo Games
2D Non-Platform Game
https://github.com/TDevvv/TEngine/assets/46716625/391420b1-7a90-4fc2-b389-99c9babab129


2D Platform Game
https://github.com/TDevvv/TEngine/assets/46716625/0b153927-d8f9-4d6c-b211-407f79ef73ac


