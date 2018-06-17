# JibJib - An Android app for matching bird sounds
JibJib is a mobile App for Android, which provides access to ornithology in a clear, minimalistic and playful manner.
The app focuses on an intuitive and clear design and workflow to guide the user through the different use cases. 

## Application workflow
When hearing a bird call the user can use his Android device to record the sound of the call. The recorded audio data is sent to the  [REST API](https:///github.com/gojibjib//jibjib-api) where it is processed and match result with the corresponding matching accuracies are returned.
On the following screen the match results are displayed comprising the scientific and common name of the bird and the matching accuracy. Each of the results can be clicked on to show more detailed information about the bird.
Store bird to local data base
On the detail view it is also possible to add the bird to the collection i.e. storing the bird into the local app data base. The local bird collection can also be viewed separately.

## Clean Architecture
The app is built using the ''clean'' architecture. This architecture divides the software into different modules by means of separation of concerns. This provides a maximum of loose coupling between the different modules. The diagram visualizes how the architecture works.
From the outer to the inner layer the implementation gets more abstract. The inwards dependency rule states that no inner ring has any dependencies of any outer ring. 
The core modules contains the business entities i.e. the objects the app works with: audio files, birds, match results etc.
In the layer ''above'' the use cases or functionalities of the app are defined. It is important to note that these two layers are completely independent of any framework meaning they don't even "know" anything Android related. The outer layers contain concrete implementations of frameworks, UI, data base and web access, etc. 
This modularization defines a sustainable architecture which makes it possible to test each layer independently from the other layers and also replace any layer without a huge impact on the rest of the code base.
