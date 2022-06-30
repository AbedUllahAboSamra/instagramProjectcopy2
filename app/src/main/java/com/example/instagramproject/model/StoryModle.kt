package com.example.instagramproject.model

data class StoryModle(var id  :              String  ,
                      var seenIds :ArrayList<String> ,
                      var sendTime :         String  ,
                      var imageOrVedioUrl:   String? ,
                      var text:              String  ,
                      var senderID:          String

                      )
