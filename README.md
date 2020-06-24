# Project 1 - *SimpleTodo App*

**SimpleTodo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Linh Tran**

Time spent: **3** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **view a list of todo items**
* [x] User can **successfully add and remove items** from the todo list
* [x] User's **list of items persisted** upon modification and and retrieved properly on app restart

The following **stretch** features are implemented:

* [ ] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/udjjVhh.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [Kap].

## Notes

* RecyclerView was tricky to understand and complicated to implement. However, I was able to understand what each method does and how the ItemsAdapter class communicate with MainActivity.
* The most challenging part was implementing LongClickListner. I had to read over the code twice to understand why we need to create an interface and how this interface communicates between MainActivity and ItemsAdapter. 
* Activity LifeCycle took me a bit of time to fully process. However, lifecycles helped me understand why we need to read and save data to file in order to keep the list persistent. 

## License

    Copyright 2020 Linh Tran

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
