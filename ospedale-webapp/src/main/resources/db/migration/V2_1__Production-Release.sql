UPDATE `lab_test` SET test_name = UPPER(test_name);
UPDATE `lab_test_panel` SET test_panel_name = UPPER(test_panel_name);
ALTER TABLE lab_requisition MODIFY REMARKS LONGTEXT;