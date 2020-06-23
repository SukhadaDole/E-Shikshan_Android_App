import os
import playsound
import pyttsx3
import speech_recognition as sr
import wikipedia
import webbrowser
import datetime
import smtplib

engine = pyttsx3.init('sapi5')                                                # to take inbuilt voices from windows.
voices = engine.getProperty('voices')
# print(voices[0].id)
engine.setProperty('voice', voices[0].id)                                  # [0] for male voice and [1] female voice


def speak(audio):
    engin.say(audio)
    engin.runAndWait()


if __name__ == "__main__":
    speak("sukhada is a good girl")


def wishMe():                                                                          # greet us according to time
    hour = int(datetime.datetime.now().hour)                                             # typecased  (0 to 24 time)
    if hour >= 0 and hour < 12:
        speak("Good morning!")

    elif hour >= 12 and hour < 18:
        speak("Good afternoon")

    else:
        speak("Good Evening!")

    speak("I am a jarvis sir . Please tell me how may I help you")


def takeCommand():                                  # it takes microphone input from user and returns string output
    r = sr.Recognizer()
    with sr.Microphone() as source:
        print("Listening")
        r.pause_threshold = 1                # seconds of non-speaking audio before a phrase is considered complete
        audio = r.listen(source)

    try:
        print("Recognizing....")
        query = r.recognize_google(audio, language='en-in')
        print(f"user said: {query}\n")

    except Exception as e:
        # print(e)

        print("say that again please")
        return "None"  # return none string
        return query


def sendEmail(to, content):
    sever = smtplib.SMTP('smtp.gmail.com', 587)                                       # smtp used for gmail to email
    sever.ehlo()
    sever.starttls()
    sever.login('sukhadadole@gmail.com', '111')
    sever.sendmail('sukhadadole@gmail.com', to, content)
    sever.close()


if __name__ == "__main__":
    wishMe()
    while True:
        query = takeCommand().lower()
         # for executing tasks based on query
        if 'wikipedia' in query:
            speak('searching wikipedia')
            query = query.replace("wikipedia", "")
            results = wikipedia.summary(query, sentences=2)
            speak("according to wikipedia")                                     # bill gates according to wikipedia
            speak(results)

    elif 'open youtube' in query:
    webbrowser.open("youtube.com")

elif 'open google' in query:
    webbrowser.open("google.com")

elif 'open stackoverflow' in query:
    webbrowser.open("stackoverflow.com")

elif 'play music' in query:
    music_dir = 'C:\\Users\\Public\\Music\\Sample Music'
    songs = os.listdir(music_dir)
    os.startfile(os.path.join(music_dir))
    print(songs)
    speak(songs)

elif 'the time' in query:
    strTime = datetime.datetime.now().strftime("%H:%M:%S")
    speak(f"The time is {strTime}")

elif 'open code ' in query:
    codepath = "C:\\Users\\seema\\PycharmProjects\\voice"
    os.startfile(codepath)

elif 'email to sukhada' in query:
    try:
        speak("what should i say ?")
        content = takeCommand()                                                           # convert audio to string
        to = "sukhadadole@gmail.com"
        sendEmail(to, content)
        speak("Email has been sent!")
    except Exception as e:
        print(e)
        speak("sorry , I am not able to send the Email ")



