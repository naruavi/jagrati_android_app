package com.example.lenovopc.jagrati;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Events extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getEvents();

        setBackOnClickListener();
        setPageTitle("Events");

    }

    private void getEvents() {
        final String classesURL = apiURL + "/events/";

        JsonArrayRequest req = new JsonArrayRequest(
                classesURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        initializeEvents(response);
                        Log.d("@@@@@", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("@@@@@", error.toString());
                        // TODO: Show error message here.
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "JWT " + jwtVal);
                return headers;
            }
        };

        queue.add(req);
    }

    private void initializeEvents(JSONArray events) {
        Log.d("@@@@", events.toString());
        for (int i=0; i < events.length(); i++) {
            try{
                    JSONObject event = events.getJSONObject(i);
                    final String id = event.getString("id");
                    String eventType = event.getString("_type");//EVENT OR MEETING
                    final String eventTitle = event.getString("title");//EVENT OR MEETING
                    final String createdAt = event.getString("created_at");//EVENT OR MEETING
                    final String eventDate = event.getString("time");//EVENT OR MEETING
                    final String eventImageURL = event.getString("image");//EVENT
                    final String eventDescription = event.getString("description");


                    //image

                    if(eventType.equals("EVENT"))
                    {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.eventslist);
                        View eventCard = getLayoutInflater().inflate(R.layout.event_card, null);

                        Button eventBtn = (Button) eventCard.findViewById(R.id.eventPanel);
                        eventBtn.setText(eventTitle);
                        TextView date = (TextView) eventCard.findViewById(R.id.dateTimeEvent);
                        date.setText(createdAt);

                        ImageView eventImageView = (ImageView) eventCard.findViewById(R.id.eventImage);

                        if (!eventImageURL.equals("null")) {
                            new DownloadImageTask(eventImageView, null, null, null).execute(eventImageURL);
                        }

                        eventBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: Change intent value
                                Intent eventDetailActivity = new Intent("com.example.lenovopc.jagrati.EVENTDETAIL");
                                Bundle bundle = new Bundle();
                                bundle.putString("eventTitle", eventTitle);
                                bundle.putString("eventDate", eventDate);
                                bundle.putString("eventCreatedAt", createdAt);
                                bundle.putString("eventURL", eventImageURL);
                                bundle.putString("eventDescription", eventDescription);

                                eventDetailActivity.putExtras(bundle);

                                startActivity(eventDetailActivity);
                            }
                        });


                        linearLayout.addView(eventCard);

                    }
                    else if(eventType.equals("MEETING"))
                    {
                        Button annoucementButton = (Button) findViewById(R.id.announcementButton);
                        annoucementButton.setText(eventTitle);
                        TextView dateannouncement = (TextView)findViewById(R.id.dateTimeAnnouncement);
                        dateannouncement.setText(createdAt);

                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.eventslist);
                        View announcementCard = getLayoutInflater().inflate(R.layout.announcement_card, null);

                        linearLayout.addView(announcementCard);

                    }




            } catch (JSONException e) {
                Log.e("@@@@", e.getMessage());
                e.printStackTrace();
            }

        }
    }

    public void addNewEvent(View view) {
        Intent classActivity = new Intent("com.example.lenovopc.jagrati.ADDEVENT");
        startActivity(classActivity);

        }




}