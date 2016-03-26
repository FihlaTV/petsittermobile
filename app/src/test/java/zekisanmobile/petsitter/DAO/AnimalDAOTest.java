package zekisanmobile.petsitter.DAO;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import io.realm.Realm;
import zekisanmobile.petsitter.BuildConfig;
import zekisanmobile.petsitter.Model.Animal;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({Realm.class})
public class AnimalDAOTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();
    Realm mockRealm;

    @Before
    public void setup() {
        mockStatic(Realm.class);

        Realm mockRealm = PowerMockito.mock(Realm.class);

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        this.mockRealm = mockRealm;
    }

    @Test
    public void shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
    }

    @Test
    public void testCreateAnimal() throws Exception {
        Animal animal = new Animal();
        when(mockRealm.createObject(Animal.class)).thenReturn(animal);

        Animal output = mockRealm.createObject(Animal.class);

        assertThat(output, is(animal));
    }

    @Test
    public void shouldVerifyTransactionWasCreated(){
        Animal animal = mock(Animal.class);
        when(mockRealm.createObject(Animal.class)).thenReturn(animal);

        AnimalDAO.createAnimal("Cão");

        // Verify that the begin transaction was called only once
        verify(mockRealm, times(1)).beginTransaction();

        // Verify that Realm#createObject was called only once
        verify(mockRealm, times(1)).createObject(Animal.class);

        // Verify that Animal#setName() is called only once
        verify(animal, times(1)).setName(Mockito.anyString());

        // Verify that the transaction was committed only once
        verify(mockRealm, times(1)).commitTransaction();

        // Verify that the Realm was closed only once.
        verify(mockRealm, times(1)).close();
    }
}