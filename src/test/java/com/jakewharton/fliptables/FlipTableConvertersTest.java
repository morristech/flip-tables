package com.jakewharton.fliptables;

import com.jakewharton.fliptables.util.FakeResultSet;
import com.jakewharton.fliptables.util.Person;
import com.jakewharton.fliptables.util.PersonType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class FlipTableConvertersTest {
  @Test public void simpleIterator() {
    List<Person> people = Arrays.asList( //
        new Person("Big", "Bird", 16, "Big Yellow"), //
        new Person("Joe", "Smith", 42, "Proposition Joe"), //
        new Person("Oscar", "Grouchant", 8, "Oscar The Grouch") //
    );
    String expected = ""
        + "╔═════╤═══════════╤═══════════╤══════════════════╗\n"
        + "║ Age │ FirstName │ LastName  │ NickName         ║\n"
        + "╠═════╪═══════════╪═══════════╪══════════════════╣\n"
        + "║ 16  │ Big       │ Bird      │ Big Yellow       ║\n"
        + "╟─────┼───────────┼───────────┼──────────────────╢\n"
        + "║ 42  │ Joe       │ Smith     │ Proposition Joe  ║\n"
        + "╟─────┼───────────┼───────────┼──────────────────╢\n"
        + "║ 8   │ Oscar     │ Grouchant │ Oscar The Grouch ║\n"
        + "╚═════╧═══════════╧═══════════╧══════════════════╝\n";
    String table = FlipTableConverters.fromIterable(people, Person.class);
    assertThat(table).isEqualTo(expected);
  }

  @Test public void simpleResultSet() throws SQLException {
    String[] headers = { "English", "Digit", "Spanish" };
    String[][] data = {
        { "One", "1", "Uno" },
        { "Two", "2", "Dos" },
        { "Three", "3", "Tres" },
    };
    ResultSet resultSet = new FakeResultSet(headers, data);
    String expected = ""
        + "╔═════════╤═══════╤═════════╗\n"
        + "║ English │ Digit │ Spanish ║\n"
        + "╠═════════╪═══════╪═════════╣\n"
        + "║ One     │ 1     │ Uno     ║\n"
        + "╟─────────┼───────┼─────────╢\n"
        + "║ Two     │ 2     │ Dos     ║\n"
        + "╟─────────┼───────┼─────────╢\n"
        + "║ Three   │ 3     │ Tres    ║\n"
        + "╚═════════╧═══════╧═════════╝\n";
    String table = FlipTableConverters.fromResultSet(resultSet);
    assertThat(table).isEqualTo(expected);
  }

  @Test public void simpleObjects() {
    String[] headers = { "First Name", "Last Name", "Age", "Type" };
    Object[][] data = { //
        { "Big", "Bird", 16, PersonType.COSTUME }, //
        { "Joe", "Smith", 42, PersonType.HUMAN }, //
        { "Oscar", "Grouchant", 8, PersonType.PUPPET } //
    };
    String expected = ""
        + "╔════════════╤═══════════╤═════╤═════════╗\n"
        + "║ First Name │ Last Name │ Age │ Type    ║\n"
        + "╠════════════╪═══════════╪═════╪═════════╣\n"
        + "║ Big        │ Bird      │ 16  │ Costume ║\n"
        + "╟────────────┼───────────┼─────┼─────────╢\n"
        + "║ Joe        │ Smith     │ 42  │ Human   ║\n"
        + "╟────────────┼───────────┼─────┼─────────╢\n"
        + "║ Oscar      │ Grouchant │ 8   │ Puppet  ║\n"
        + "╚════════════╧═══════════╧═════╧═════════╝\n";
    String table = FlipTableConverters.fromObjects(headers, data);
    assertThat(table).isEqualTo(expected);
  }
}
