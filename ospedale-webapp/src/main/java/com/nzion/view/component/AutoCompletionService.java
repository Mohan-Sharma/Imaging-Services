package com.nzion.view.component;

import java.util.List;

public interface AutoCompletionService {

	List<AutoCompletionItem> search(String query);

	Object convertToObject(AutoCompletionItem selItem);
}
