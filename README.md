# ChatMessagesAdapter for Android
QuickBlox simple to use UI library for showing quickblox chat messages inside android application. 

#Features
- Ready-to-go QBChatMessage view adapter with a set of view types.
- UI customisation  for all message types.
- Flexibility in improving and extending functionality.
- Easy to connect with Quickblox.
- Optimised and performant.
- Flexible mechainm for styling layout for chat messages.
- Add custom widgets inside predefined layout

# Screenshots

<img src="screenshots/screenshot1.png" border="5" alt="Chat Message Adapter" width="300">

# Dependencies
Just add to your build.gradle
```xml
dependencies {

compile 'com.quickblox:chat-message-adapter:1.1.0'

}
```
# Getting started
Example is included in repository. Try it out to see how chat message adapter works.  
For now QBMessagesAdapter works with [RecycleView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html).

The following code example demonstrates how to add the QBMessagesAdapter to show chat messages:

 - Include [RecycleView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html) into activity layout:
```xml
<!-- A RecyclerView with some commonly used attributes -->
<android.support.v7.widget.RecyclerView
    android:id="@+id/list_chat_messages"
    android:scrollbars="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
 - Once you have added a RecyclerView widget to your layout, get it and attach a QBMessagesAdapter for the data to be displayed:
```java
   public class ChatActivity extends AppCompatActivity {

   private RecyclerView messagesListView;
   private QBMessagesAdapter chatAdapter;

   private List<QBChatMessage> messages = ..;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        messagesListView = (RecyclerView) findViewById(R.id.list_chat_messages);

        //retrieve messages from storage and set to adapter
        chatAdapter = new QBMessagesAdapter(ChatActivity.this, messages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this, VERTICAL, false);
        messagesListView.setLayoutManager(layoutManager);
        messagesListView.setAdapter(chatAdapter);
    }
```

Steps to customize QBMessagesAdapter to your Chat app:

1. Create a subclass of QBMessagesAdapter.
2. In your subclass define and override methods that you need (such as displayAvatarImage() and obtainAvatarUrl()).
3. Also you can adjust predefined styles for the next:
```xml
  * size of the avatar image view
  * message view container size
  * attachment view container size
  * bubble background for message
  * for all layouts and views set paddings, margins, etc.
```

## Main listeners

Chat Adapter main event listeners.
```java
QBChatMessageLinkClickListener - interface used to handle Clicks and Long clicks on the TextView and taps on the phone, web, mail links inside of TextView.
QBChatAttachImageClickListener - interface used to handle Clicks on image attachments.
QBChatAttachLocationClickListener - interface used to handle Clicks on location attachments.
```  
Usage:
```java
messagesAdapter.setAttachLocationClickListener(new QBChatAttachLocationClickListener{

        @Override
        public void onLinkClicked(QBAttachment qbAttachment, int i) {
            MapsActivity.start(context, qbAttachment.getData());
        }
    });

messagesAdapter.setMessageTextViewLinkClickListener(messagesTextViewLinkClickListener, false);
messagesAdapter.setAttachImageClickListener(imageAttachClickListener);
```  

## Style configuration
  
**ChatMessagesAdapter** has a flexible configuration system for displaying any view elements. For example:

To change bubble for left side opponent you can just create
```xml
    <style name="BubbleTextFrame.Left">
        <item name="android:background">@drawable/left_bubble</item>
    </style>
```
To change Avatar cell size
```xml
    <style name="AvatarImageViewStyle.Left">
        <item name="android:layout_width">@dimen/image_view_small_avatar_layout_width</item>
        <item name="android:layout_height">@dimen/image_view_small_avatar_layout_width</item>        
    </style>
```
To change some margin or padding
```xml
    <style name="ImageViewAttach.Left">
        <item name="android:layout_marginLeft">@dimen/padding_common</item>
    </style>
```

Some styles namespaces:
 * BubbleTextFrame (Right or Left) - for LinearLayout in widget text message with background bubble, that includes timetext, msgtext, widget views
 * BubbleAttachFrame (Right or Left) - for RelativeLayout in item attach with background bubble, that includes progressbar, image, timetext views
 * AvatarImageViewStyle (Right or Left) - for avatar CircleImageView
 * ListItemTextMessage (Right or Left) - for item text message MessageTextView
 * ListItemAttachMessage (Right or Left) - for item attach message
 * ProgressBarAttach (Right or Left) - for progressbar in attach
 * ImageViewAttach (Right or Left) - for image in attach
 * TextViewAttach (Right or Left) - for text in attach<br />
 <br />
 * WidgetTextMsgFrame - style for main FrameLayout text message
 * LinearTextMsgFrame - style for LinearLayout that includes all text message views
 
##Configure QBMessagesAdapter
You can modify layout resource files, that used in QBMessagesAdapter by creating them with the same namespaces.

In addition, you can add your own widget, just create the layout resource file.  
For example, create layout with namespace `list_item_text_right` with any layout, e.g. `text_widget_owner`:
```xml
<com.quickblox.ui.kit.chatmessage.adapter.widget.MessageTextViewRight
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:custom="http://schemas.android.com/apk/res-auto"

    android:id="@+id/msg_message_text_view_right"
    style="@style/ListItemTextMessage.Right"
    custom:widget_id="@layout/text_widget_owner">

</com.quickblox.ui.kit.chatmessage.adapter.widget.MessageTextViewRight>
```

Then you can extends QBMessagesAdapter and define your own logic for every item view - plain text message, message with image attachment or your custom item view:
```java
    @Override
    protected void onBindViewMsgRightHolder(TextMessageHolder holder, QBChatMessage chatMessage, int position) {
        //update logic for showing your own plain text message
        TextView view = (TextView) holder.itemView.findViewById(R.id.custom_text_view);
        view.setText(currentUser.getFullName());
        super.onBindViewMsgRightHolder(holder, chatMessage, position);
    }
```
```java
    @Override
    protected void onBindViewMsgLeftHolder(TextMessageHolder holder, QBChatMessage chatMessage, int position) {
        super.onBindViewMsgLeftHolder(holder, chatMessage, position);
        //update logic for showing plain text message from opponent
    }
```
```java
    @Override
    protected void onBindViewAttachLeftHolder(ImageAttachHolder holder, QBChatMessage chatMessage, int position) {
        super.onBindViewAttachLeftHolder(holder, chatMessage, position);
        //update logic for showing message with image attachment from opponent
    }
```

There are 4 predefined view types:
 - TYPE_TEXT_RIGHT
 - TYPE_TEXT_LEFT
 - TYPE_ATTACH_RIGHT
 - TYPE_ATTACH_LEFT


To create your own non predefined view type for hat message use code:

* Define custom view type for position
```java
    @Override
    protected int customViewType(int position) {
        return MY_VIEW_TYPE;
    }
```  
* Create view holder for item:
```java
    @Override
    protected QBMessageViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        if (MY_VIEW_TYPE == viewType) {
            // create ViewHolder by your own
        }
        return yourViewHolder;
    }
```  
* Fill your own view type with chat message data use code:
```java
    @Override
    protected void onBindViewCustomHolder(QBMessageViewHolder holder, QBChatMessage chatMessage, int position) {
        // here you will receive your own ViewHolder    
    }
```


Also you can override methods to display attach images
```java
    @Override
    protected void displayAttachment(QBMessageViewHolder holder, int position) {
       super.displayAttachment(holder, position);
       ...add own logic
    }
```
override methods to display avatars
```java
    @Override
    public void displayAvatarImage(String url, ImageView imageView) {
       ...
    }

    @Override
    public String obtainAvatarUrl(int valueType, QBChatMessage chatMessage) {
        return currentUser.getId().equals(chatMessage.getSenderId()) ?
                currentUserData.getUserAvatar() : opponentUserData.getUserAvatar();
    }
```
and etc.

## Location attachment integration.

Starting from version 1.1.0 ***ChatAdapter*** has ability to create location JSON, that represents such fields as lat and lng:
```java
	"attachments":[  
            {  
               "type":"location",
               "data":"{\"lat\":\"50.014141\",\"lng\":\"36.229058\"}"
            }
         ]
```
This means you can generate json location like this:
```java
double latitude = 50.014141;
double longitude = 36.229058;
String location = LocationUtils.generateLocationJson(new Pair<>("lat", latitude),
                            new Pair<>("lng", longitude));
```
And then create attachment:
```java
QBAttachment attachment = new QBAttachment("location");
attachment.setData(location);
...
chatMessage.addAttachment(attachment);
```

After receiving message with location ***ChatAdapter*** generates locationUrl type of google static maps api, to show it like an image.

***Note, you should set google_static_maps_key in string resource(https://developers.google.com/maps/documentation/static-maps/) like this:***
```java
    <string name="google_static_maps_key" templateMergeStrategy="preserve" translatable="false">
        YOUR_KEY
    </string>
```

Then by clicking this item, using ***QBChatAttachLocationClickListener***, you can get latitude and longitude to show it in your MapActivity:
```java
	String receivedLocation = attachment.getData();
	// latitude - latLngPair.first, longitude - latLngPair.second
	Pair<Double, Double> latLngPair = LocationUtils.getLatLngFromJson(receivedLocation);
```

Besides, ***LocationUtils*** has defaultUrlLocationParams for location URI and it can be customized with LocationUtils.BuilderParams(), or can be changed just some parameters in string resource:
```java
    <string name="map_zoom">15</string>
    <string name="map_size">600x300</string>
    <string name="map_type">roadmap</string>
    <string name="map_color">blue</string>
```

# License
See [LICENSE](LICENSE)
