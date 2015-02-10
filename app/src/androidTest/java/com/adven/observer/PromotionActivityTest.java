package com.adven.observer;

import android.app.Activity;
import android.app.Notification;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.urban.activity.PromotionActivity;
import com.urban.data.Action;
import com.urban.data.dao.DAO;
import com.urban.observer.R;
import com.urban.service.gcm.UrbanGcmIntentService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ServiceController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Config(manifest = "./src/main/AndroidManifest.xml")
@RunWith(MyRunner.class)
public class PromotionActivityTest {

    @Test
    public void recentShouldBeVisible() throws Exception {
        Activity activity = Robolectric.buildActivity(PromotionActivity.class).create().get();
        ListView listView = (ListView) activity.findViewById(R.id.promos_recent_list);
        int expectedCount = DAO.getAll(Action.class).size();
        int actualCount = listView.getAdapter().getCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void recentShouldContainElementsFromNotifications() throws Exception {

        ServiceController<UrbanGcmIntentService> controller = Robolectric.buildService(UrbanGcmIntentService.class);
        UrbanGcmIntentService service = controller.get();
        List<Action> list = new ArrayList<Action>();
        Notification notification = service.createNotification(list);
        service.sendNotification(123, notification);

        //getNotifications
        //clickOnNotification;
        //receiveIntentFromBroadcast
        //StartParametrizedActivityFromIntent

        PromotionActivity activity = Robolectric.buildActivity(PromotionActivity.class).create().get();
        ListView listView = (ListView) activity.findViewById(R.id.promos_recent_list);




    }

    @Test
    public void recentShouldBeSortedByBeginDateAndEndDate() throws Exception {
        Activity activity = Robolectric.buildActivity(PromotionActivity.class).create().get();
        ListView listView = (ListView) activity.findViewById(R.id.promos_recent_list);

        ArrayList<Action> promos = new ArrayList<Action>();

        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            promos.add((Action)listView.getAdapter().getItem(i));
        }

        ArrayList<Action> sortedPromos = new ArrayList<Action>();
        Collections.copy(sortedPromos, promos);
        Collections.sort(sortedPromos, PROMOTION_COMPARATOR);

        assertTrue(promos.equals(sortedPromos));
    }

    @Test
    public void recentElementShouldContainPromoSubjectAndOrganizationNameAndBeginEndDates() throws Exception {
        Activity activity = Robolectric.buildActivity(PromotionActivity.class).create().get();
        ListView listView = (ListView) activity.findViewById(R.id.promos_recent_list);

        Action action = (Action)listView.getAdapter().getItem(0);
        View view = listView.getAdapter().getView(0, null, null);

        TextView title = (TextView)view.findViewById(R.id.promo_item_title);
        TextView organizationName = (TextView)view.findViewById(R.id.promo_item_organization_name);
        TextView startDate = (TextView)view.findViewById(R.id.promo_item_start_date);
        TextView endDate = (TextView)view.findViewById(R.id.promo_item_end_date);

        assertTrue(title.equals(action.getSubject()));
        assertTrue(organizationName.equals(action.getOrganization().getName()));
        assertTrue(startDate.equals(action.getStartDate())/*???*/);
        assertTrue(endDate.equals(action.getEndDate()));

    }

    @Test
    public void shouldAppearInfoAfterClickOnExpandIcon() throws Exception {


    }

    @Test
    public void onRecentElementClickShouldGoToOrganizationScreenPromoTab() throws Exception {


    }

    /*
    * 1. Есть список последних
    * 2. В последних ровно те же элементы, что в нотификации
    * 3. отсортированы по дате начала + по дате конца
    * 4. По клику на специконку появляется допинформация
    * 5. Элемент списка должен содержать название акции и дату началаконца
    * 6. По клику на элемент списка переходим на страницу органзации, вкладка акции
    *
    * 1. Есть список всех
    * 2. В списке должны отображаться только релевантные элементы (дата конца >=  текущей)
    * 3. отсортированы по дате начала + по дате конца
    * 4. По клику на специконку появляется допинформация
    * 5. Элемент списка должен содержать название акции и дату началаконца
    * 6. По клику на элемент списка переходим на страницу органзации, вкладка акции
    *
    * 1. Присутствует фильтрация
    * 2. При вводе в поле фильтра, должна происходить фильтрация списка
    *   2.1.  "последние" по организации и заголовку акции
    *   2.2.  "все" по организации и заголовку акции
    *
    * 1. Присутствует кнопка открытия бокового меню
    * 2. При нажатии на нее открывается боковое меню
    *
    * */


    private static final Comparator<Action> PROMOTION_COMPARATOR = new Comparator<Action>() {
        @Override
        public int compare(Action a1, Action a2) {
            return -1;
        }
    };


}