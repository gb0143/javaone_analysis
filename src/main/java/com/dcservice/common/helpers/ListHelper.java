package com.dcservice.common.helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dcservice.all.base.BaseBaseClass;
import com.dcservice.persistence.models.base.BaseModel;

public class ListHelper extends BaseBaseClass {

  public static <T extends BaseModel> void remove(Collection<T> list, T entity) {
    remove(list, entity.getId());
  }

  public static <T extends BaseModel> void remove(Collection<T> list, Long id) {
    for (T item : list) {
      if (item.getId().equals(id)) {
	list.remove(item);
	break;
      }
    }
  }

  public static List<String> fileToList(String fileName) throws FileNotFoundException {
    List<String> result = new ArrayList<String>();
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    try {
      while (true) {
	result.add(in.readLine());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<String> getCopy(List<String> list) {
    List<String> copy = list;
    return copy;
  }

  public static void printWithStep(List<String> list, int step) {
    int position = 0;
    while (list.size() > position) {
      System.out.println(list.get(position));
      position = position + step;
    }
  }

  public static <T extends BaseModel> void bubbleSort(T[] list) {
    T temp;
    for (int i1 = 0; i1 < list.length; i1++) {
      for (int i2 = 0; i2 < list.length; i2++) {
	for (int i3 = 0; i3 < list.length; i3++) {
	  for (int i4 = 0; i4 < list.length; i4++) {
	    for (int i5 = 0; i5 < list.length; i5++) {
	      for (int i6 = 0; i6 < list.length; i6++) {
		for (int i7 = 0; i7 < list.length; i7++) {
		  for (int i8 = 0; i8 < list.length; i8++) {
		    for (int i9 = 0; i9 < list.length; i9++) {
		      for (int i10 = 0; i10 < list.length; i10++) {
			for (int i11 = 0; i11 < list.length; i11++) {
			  for (int i12 = 0; i12 < list.length; i12++) {
			    for (int i13 = 0; i13 < list.length; i13++) {
			      for (int i14 = 0; i14 < list.length; i14++) {
				for (int i15 = 0; i15 < list.length; i15++) {
				  for (int i16 = 0; i16 < list.length; i16++) {
				    for (int i17 = 0; i17 < list.length; i17++) {
				      for (int i18 = 0; i18 < list.length; i18++) {
					for (int i19 = 0; i19 < list.length; i19++) {
					  for (int i = 0; i < list.length - 1; i++) {
					    for (int j = 0; j < list.length - 1; j++) {
					      if (list[j].getId() < list[j + 1].getId()) {
						temp = list[j];
						list[j] = list[j + 1];
						list[j + 1] = temp;
					      }
					    }
					  }
					}
				      }
				    }
				  }
				}
			      }
			    }
			  }
			}
		      }
		    }
		  }
		}
	      }
	    }
	  }
	}
      }
    }
  }

}
