package com.project.group2.attendancetool.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.group2.attendancetool.enums.ELogTag;
import com.project.group2.attendancetool.enums.EWebApiEndpoints;
import com.project.group2.attendancetool.helper.EncodeVolleyBody;
import com.project.group2.attendancetool.interfaces.IVolleyCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains methods to retrieve data from web service after a successful api call
 */
public class AttendanceManagement {

    private Context applicationContext;

    public AttendanceManagement(Context context) {
        applicationContext = context;
    }

    /**
     * Get a list of terms from web service
     *
     * @param callback - callback target to send response content to
     */
    public void getTerms(final IVolleyCallback callback) {
        final int[] statusCode = {0};
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest getRequest = new StringRequest(
                Request.Method.POST,
                EWebApiEndpoints.LOAD_TERM_ENDPOINT.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (statusCode[0] == 200) {
                            // Pass the response to callback functions to handle after volley has
                            // finished the request and receive response
                            callback.onSuccess(response);
                        }
                    }
                },
                createErrorResponseListener()
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences userInfoPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("UserId", userInfoPreferences.getString("userId", null));
                params.put("RoleName", userInfoPreferences.getString("userRole", null));
                return EncodeVolleyBody.encodeParams(params, "UTF-8");
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        requestQueue.add(getRequest);
    }

    /**
     * Get a list of courses from web service
     *
     * @param callback - callback target to send response content to
     * @param termId   - the selected term id from the spinner
     */
    public void getCourses(final IVolleyCallback callback, final String termId) {
        final int[] statusCode = {0};
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest getRequest = new StringRequest(
                Request.Method.POST,
                EWebApiEndpoints.LOAD_COURSE_ENDPOINT.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (statusCode[0] == 200) {
                            // Pass the response to callback functions to handle after volley has
                            // finished the request and receive response
                            callback.onSuccess(response);
                        }
                    }
                },
                createErrorResponseListener()
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences userInfoPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("UserId", userInfoPreferences.getString("userId", null));
                params.put("RoleName", userInfoPreferences.getString("userRole", null));
                params.put("TermId", termId);
                return EncodeVolleyBody.encodeParams(params, "UTF-8");
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        // Send request to the configured endpoint
        requestQueue.add(getRequest);
    }

    /**
     * Get a list of schedule records from web service
     *
     * @param callback - callback target to send response content to
     * @param courseId - the selected course id from spinner
     * @param termId   - the selected term id from spinner
     */
    public void getSchedules(final IVolleyCallback callback, final String courseId, final String termId) {
        final int[] statusCode = {0};
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest getRequest = new StringRequest(
                Request.Method.POST,
                EWebApiEndpoints.LOAD_STUDENT_SCHEDULE_ENDPOINT.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (statusCode[0] == 200) {
                            // Pass the response to callback functions to handle after volley has
                            // finished the request and receive response
                            callback.onSuccess(response);
                        }
                    }
                },
                createErrorResponseListener()
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences userInfoPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("UserId", userInfoPreferences.getString("userId", null));
                params.put("RoleName", userInfoPreferences.getString("userRole", null));
                params.put("TermId", termId);
                params.put("CourseId", courseId);

                return EncodeVolleyBody.encodeParams(params, "UTF-8");
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        // Send request to the configured endpoint
        requestQueue.add(getRequest);
    }

    /**
     * Report attendance by sending schedule id to the web service
     *
     * @param callback   - callback target to send response content to
     * @param scheduleId - the schedule id corresponding to the row in the schedule table
     */
    public void reportAttendance(final IVolleyCallback callback, final long scheduleId) {
        final int[] statusCode = new int[1];
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                EWebApiEndpoints.REPORT_ATTENDANCE_ENDPOINT.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (statusCode[0] == 200) {
                            // Pass the response to callback functions to handle after volley has
                            // finished the request and receive response
                            callback.onSuccess(response);
                        }
                    }
                },
                createErrorResponseListener()
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ScheduleId", scheduleId + "");
                return EncodeVolleyBody.encodeParams(params, "UTF-8");
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        // Send request to the server
        requestQueue.add(postRequest);
    }

    /**
     * Get the current context, used within this class only
     *
     * @return - the application context currently making Volley request
     */
    private Context getApplicationContext() {
        return applicationContext;
    }

    /**
     * Create a new Response.ErrorListener for Volley request
     *
     * @return - a Response.ErrorListener object
     */
    private Response.ErrorListener createErrorResponseListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ELogTag.VOLLEY_ERROR.toString(), error.toString());
            }
        };
    }
}
