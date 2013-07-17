<%--

Copyright (c) 2012-2013, The University of Edinburgh.
All Rights Reserved

Additional Model attrs:

deliverySettingsList
deliverySettingsListRouting: dsid -> action -> URL

--%>
<%@ include file="/WEB-INF/jsp/includes/pageheader.jspf" %>
<page:page title="Test Delivery Settings">

  <header class="actionHeader">
    <nav class="breadcrumbs">
      <a href="${utils:escapeLink(primaryRouting['dashboard'])}">QTIWorks Dashboard</a> &#xbb;
      <a href="${utils:escapeLink(primaryRouting['deliverySettingsManager'])}">Delivery Settings Manager</a> &#xbb;
    </nav>
    <h2>Test Delivery Settings</h2>
  </header>
  <div class="hints">
    <p>
      Your Delivery Settings for running Assessment Tests are shown below.
    </p>
  </div>
  <table class="listTable">
    <thead>
      <tr>
        <th></th>
        <th>Name</th>
        <th>Created</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="deliverySettings" items="${deliverySettingsList}" varStatus="loopStatus">
        <c:set var="deliverySettingsRouting" value="${deliverySettingsListRouting[deliverySettings.id]}"/>
        <c:set var="areBeingUsed" value="${!empty thisAssessment && !empty theseDeliverySettings && theseDeliverySettings.id==deliverySettings.id}"/>
        <tr>
          <td class="bigStatus">${loopStatus.index + 1}</td>
          <td align="center">
            <h4>
              <a href="${utils:escapeLink(deliverySettingsRouting['showOrEdit'])}">
                <c:out value="${deliverySettings.title}"/>
              </a>
            </h4>
          </td>
          <td class="center">
            <c:out value="${utils:formatDateAndTime(deliverySettings.creationTime)}"/>
          </td>
        </tr>
      </c:forEach>
      <tr>
        <td class="plus"></td>
        <td colspan="2" align="center" class="actions">
          <a href="${utils:escapeLink(primaryRouting['createTestDeliverySettings'])}">Create new Delivery Settings for running an Assessment Test</a>
        </td>
      </tr>
    </tbody>
  </table>

</page:page>
