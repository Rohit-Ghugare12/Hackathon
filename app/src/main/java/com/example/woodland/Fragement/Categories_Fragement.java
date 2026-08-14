package com.example.woodland.Fragement;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.woodland.AdapterClass.getAllProductAdapterClass;
import com.example.woodland.COMMON.Urls;
import com.example.woodland.POJOClass.getAllProductPOJOClass;
import com.example.woodland.R;
import com.example.woodland.contact_us;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class Categories_Fragement extends Fragment {


    ImageSlider imageSlider;
    TextView tvCategoryNoProductFound;

    ListView lvCategoryAllproduct;
    SearchView searchView;
    List<getAllProductPOJOClass> getAllProductPOJOClass;
    getAllProductAdapterClass getAllProductAdapterClass;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_categories__fragement, container, false);
        Toast.makeText(getActivity(),"Chat",Toast.LENGTH_SHORT).show();
        imageSlider=view.findViewById(R.id.isImageSlider);
        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();

        slideModelArrayList.add(new SlideModel(R.drawable.my_logo,"Welcome To Woodland", ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.mango_tree,"Welcome To Woodland", ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img,"Welcome To Woodland", ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel("https://www.bing.com/th/id/OIP.TcjjaHtnUgAeXvUuthm5xAHaI3?w=193&h=231&c=8&rs=1&qlt=90&o=6&dpr=1.4&pid=ImgAns&rm=2","Welcome To Woodland", ScaleTypes.CENTER_CROP));


        imageSlider.setImageList(slideModelArrayList, ScaleTypes.CENTER_CROP);
        imageSlider.setSlideAnimation(AnimationTypes.CUBE_IN);

        progressDialog =new ProgressDialog(getActivity());
        progressDialog.setTitle("Product Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        lvCategoryAllproduct=view.findViewById(R.id.lvCategoryAllproduct);
        tvCategoryNoProductFound=view.findViewById(R.id.tvCategoryNoProductFound);
        getAllProductPOJOClass=new ArrayList<>();

        getAllProduct();


        searchView=view.findViewById(R.id.svCategorySearchProduct);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query)
            {
                searchProduct(query);

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchProduct(query);
                return false;
            }


        });

        return view;
    }
    private void searchProduct(String query)
    {
        List<getAllProductPOJOClass> tempSearchProduct = new ArrayList<>();
        tempSearchProduct.clear();

        for (getAllProductPOJOClass obj:getAllProductPOJOClass)
        {
            if (obj.getProduct_name().toUpperCase().contains(query.toUpperCase()));

        }
        getAllProductAdapterClass = new getAllProductAdapterClass(tempSearchProduct,getActivity());
        lvCategoryAllproduct.setAdapter(getAllProductAdapterClass);
    }

    private void getAllProduct()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params =new RequestParams();

        client.post(Urls.getAllProductAPI,params,new JsonHttpResponseHandler()

        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);

                progressDialog.dismiss();
                try {
                    JSONArray jsonArray= response.getJSONArray("getAllProduct");

                    for (int i = 0; i < jsonArray.length(); i++)
                    {

                        JSONObject jsonObject= jsonArray.getJSONObject(i);

                        String strId = jsonObject.getString("id");
                        String strproduct_name = jsonObject.getString("product_name");
                        String strproduct_image= jsonObject.getString("product_image");
                        String strproduct_category = jsonObject.getString("product_category");
                        String strproduct_price = jsonObject.getString("product_price");
                        String strproduct_rating = jsonObject.getString("product_rating");

                        getAllProductPOJOClass.add(new getAllProductPOJOClass(strId,strproduct_name,strproduct_image,strproduct_category,strproduct_price,strproduct_rating));

                    }

                    getAllProductAdapterClass = new getAllProductAdapterClass(getAllProductPOJOClass, getActivity());
                    lvCategoryAllproduct.setAdapter(getAllProductAdapterClass);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Toast.makeText(getActivity(),
                        "Status: " + statusCode + "\n" + throwable.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}