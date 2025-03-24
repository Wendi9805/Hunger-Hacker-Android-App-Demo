package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.dataGraphicalViewer.DataGraphicalFunction;
import com.example.groupproject.dataProfileFunction.ProfileActivity;
import com.example.groupproject.loadDataFunction.FoodData;
import com.example.groupproject.searchFunction.RedBlackTree;
import com.example.groupproject.searchFunction.Exp;
import com.example.groupproject.searchFunction.Parser;
import com.example.groupproject.searchFunction.ProductSearchIndex;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
/**
 * @author Ke Wen
 */
public class SearchPage extends AppCompatActivity {
    private RecyclerView searchResult;
    private Button searchButton;
    private Button filterButton;
    private EditText searchBar;
    private RedBlackTree dataTree;
    private SearchAdapter adapter;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ProductSearchIndex productSearchIndex;
    private String currentFilterCategory;
    private List<FoodData> currentSearchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        searchButton = findViewById(R.id.Sbutton);
        searchBar = findViewById(R.id.searchBar);
        searchResult = findViewById(R.id.searchResult);
        filterButton = findViewById(R.id.Fbutton);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        productSearchIndex = ProductSearchIndex.getInstance();
        dataTree = productSearchIndex.getDataTree();

        adapter = new SearchAdapter(new ArrayList<>());
        searchResult.setLayoutManager(new LinearLayoutManager(this));
        searchResult.setAdapter(adapter);

        searchButton.setOnClickListener(v -> performSearch());
        filterButton.setOnClickListener(this::applyFilter);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        //buttons to jump from one page to another
        Button buttonSearchButton = findViewById(R.id.searchButton);
        Button loadButton = findViewById(R.id.homeButton);
        Button userListButton = findViewById(R.id.userListButton);
        Button profileButton = findViewById(R.id.profileB);
        Button dataButton = findViewById(R.id.data);
        Button infoButton = findViewById(R.id.infoButton);


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(v, "You can type the name of product, the provider's name or the address for searching, you also can use '+' and '-' to combine these keywords. E.g: apple+coles-act (means you wanna search all apple products which provided by coles and not in ACT area)", Snackbar.LENGTH_INDEFINITE);
                View snackbarView = snackbar.getView();
                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setMaxLines(6);
                snackbar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        snackbar.dismiss();
                    }
                }, 10000); // 10000 milliseconds = 10 seconds
            }
        });


        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPage.this, LoadActivity.class);
                startActivity(intent);

            }

        });
        buttonSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPage.this, SearchPage.class);
                startActivity(intent);
            }

        });
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPage.this, UserListActivity.class);
                startActivity(intent);
            }

        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPage.this, ProfileActivity.class);
                startActivity(intent);
            }

        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPage.this, DataGraphicalFunction.class);
                startActivity(intent);
            }
        });
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        currentFilterCategory = mapMenuItemToCategory(menuItem.getItemId());
        performFilter();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private String mapMenuItemToCategory(int menuItemId) {

        String resourceName = getResources().getResourceEntryName(menuItemId);

        // return category according to resourceName
        if (resourceName.equals("category_baby")) {
            return "baby";
        } else if (resourceName.equals("category_bakery")) {
            return "bakery";
        } else if (resourceName.equals("category_dairy")) {
            return "dairy";
        } else if (resourceName.equals("category_frozen")) {
            return "frozen";
        } else if (resourceName.equals("category_fruit_and_vegetable")) {
            return "fruit";
        } else if (resourceName.equals("category_freezer")) {
            return "freezer";
        } else if (resourceName.equals("category_fresh")) {
            return "fresh";
        } else if (resourceName.equals("category_pantry")) {
            return "pantry";
        } else if (resourceName.equals("category_health_and_beauty")) {
            return "Health and Beauty";
        } else if (resourceName.equals("category_household")) {
            return "Household";
        } else if (resourceName.equals("category_ngo")) {
            return "NGO";
        } else if (resourceName.equals("category_university")) {
            return "university";
        } else if (resourceName.equals("category_community")) {
            return "Community";
        } else {
            return "";
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle the new intent here
    }
    private void applyFilter(View view) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        Log.d("SearchPage", "Filter button clicked");

        performFilter();
    }

    private void performFilter() {
        if (currentFilterCategory != null && !currentFilterCategory.isEmpty()) {
            List<FoodData> filteredData = currentSearchResults.stream()
                    .filter(foodData -> foodData.getCategory().toLowerCase().contains(currentFilterCategory.toLowerCase()))
                    .collect(Collectors.toList());
            List<String> formattedData = formatDisplayData(filteredData);
            handler.post(() -> updateListView(formattedData)); // 在UI线程更新列表
        } else {
            showErrorMessage("No filter category selected.");
        }
    }

    private void performSearch() {
        String searchText = getSearchBarText();
        Log.d("SearchPage", "Search Text: " + searchText);
        if (!searchText.isEmpty()) {
            currentSearchResults.clear();
            executorService.execute(() -> {
                try {
                    Parser parser = new Parser(searchText);
                    Exp resultExp = parser.performParse();

                    Map<String, Set<FoodData>> indexedData = productSearchIndex.getDataTree().buildIndex();
                    Log.d("SearchPage", "search data: " + productSearchIndex.getAllFoodData().toString());
                    Set<FoodData> results = resultExp.evaluate(indexedData);

                    currentSearchResults.clear();
                    currentSearchResults.addAll(results);
                    Log.d("SearchPage", "search result: " + results.toString());

                    // updata UI
                    List<String> displayResults = formatDisplayData(new ArrayList<>(results));
                    handler.post(() -> updateListView(displayResults));
                } catch (Parser.IllegalProductException e) {
                    Log.e("SearchPage", "Parsing error: " + e.getMessage(), e); // 打印解析错误和异常
                    handler.post(() -> showErrorMessage("Parsing error: " + e.getMessage()));
                }
            });
        } else {
            Log.d("SearchPage", "Empty search input"); // 搜索输入为空
        }
    }

    private List<String> formatDisplayData(List<FoodData> foodDataList) {
        return foodDataList.stream()
                .map(foodData -> String.format("%s - %s, %s (%s)",
                        foodData.getProductName(),
                        foodData.getProvider(),
                        foodData.getAddress(),
                        foodData.getCategory()))
                .collect(Collectors.toList());
    }

    private void updateListView(List<String> displayData) {
        runOnUiThread(() -> {
            adapter.setData(displayData);
            adapter.notifyDataSetChanged();
        });
    }

    public String getSearchBarText() {
        return searchBar.getText().toString().trim();
    }

    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
        private List<String> mData;

        public SearchAdapter(List<String> data) {
            mData = data;
        }

        public void setData(List<String> data) {
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String item = mData.get(position);
            holder.textView.setText(item);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }


    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
